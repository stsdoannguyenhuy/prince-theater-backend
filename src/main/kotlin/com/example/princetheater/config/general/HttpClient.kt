package com.example.princetheater.config.general

import com.example.princetheater.exception.InternalError
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.http.Header
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.conn.ConnectTimeoutException
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.message.BasicHeader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets

@Component
class HttpClient {
    private val logger = org.apache.logging.log4j.LogManager.getLogger(HttpClient::class)

    companion object {
        private const val TIME_OUT = 5000
        private const val MAX_RETRY = 3
        private const val SUCCESS_CODE = 200
    }

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    fun get(url: String, headers: List<Header> = ArrayList()): String {
        val httpGet = HttpGet(url)
        headers.forEach { httpGet.addHeader(it) }
        val httpclient = HttpClientBuilder
            .create()
            .setDefaultRequestConfig(
                RequestConfig.custom()
                    .setConnectTimeout(TIME_OUT)
                    .setSocketTimeout(TIME_OUT).build()
            )
            .build()
        val response = retry { httpclient.execute(httpGet) }
        if (isSuccess(response)) {
            return String(response.entity.content.readAllBytes(), StandardCharsets.UTF_8)
        }
        throw UnknownError("Unknown error when get from at $url with ${response.statusLine}")
    }

    fun <T> get(url: String, headers: List<Header> = ArrayList(), clazz: Class<T>): T {
        val stringResponse: String = get(url,headers)
        return objectMapper.readValue(stringResponse, clazz)
    }

    private fun retry(task: Function0<CloseableHttpResponse>): CloseableHttpResponse {
        var currentRetry = 0
        var response: CloseableHttpResponse? = null
        while (currentRetry < MAX_RETRY) {
            var isTimeout = false
            try {
                response = task.invoke()
            } catch (ex: ConnectTimeoutException) {
                isTimeout = true
            }
            if (isRetry(isTimeout, response)) {
                logger.error("Retry at time: $currentRetry with status: ${response?.statusLine?.statusCode}")
                currentRetry += 1
                continue
            } else {
                return response!!
            }
        }
        return response ?: throw InternalError("Time out exception when try to call API")
    }

    private fun isRetry(isTimeOut: Boolean, response: CloseableHttpResponse?): Boolean {
        return isTimeOut || response?.statusLine?.statusCode != SUCCESS_CODE
    }

    private fun isSuccess(response: CloseableHttpResponse): Boolean {
        return response.statusLine.statusCode == SUCCESS_CODE
    }

}
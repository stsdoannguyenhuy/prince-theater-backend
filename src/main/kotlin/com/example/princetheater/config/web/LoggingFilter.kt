package com.example.princetheater.config.web

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class LoggingFilter : Filter {
    private val logger = LogManager.getLogger(LoggingFilter::class)
    @Autowired
    lateinit var objectMapper:ObjectMapper
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val httpRequest: HttpServletRequest = request as HttpServletRequest
        logger.info("receive request ${httpRequest.servletPath} with param: ${objectMapper.writeValueAsString(request.parameterMap)}")
        chain?.doFilter(request, response)
        val httpResponse = response as HttpServletResponse
        logger.info("response of request ${httpRequest.servletPath} with status: ${httpResponse.status}")
    }
}
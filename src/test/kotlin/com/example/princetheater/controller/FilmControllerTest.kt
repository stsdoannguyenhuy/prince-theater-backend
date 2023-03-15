package com.example.princetheater.controller

import com.example.princetheater.exception.BadRequestException
import com.example.princetheater.model.BaseResponse
import com.example.princetheater.model.FilmResponse
import com.example.princetheater.services.FilmService
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.test.util.ReflectionTestUtils
import java.util.*


@SpringBootTest
class FilmControllerTest {
    @Autowired
    private lateinit var filmController: FilmController
    private lateinit var wireMockServer: WireMockServer


    @Test
    fun testGetFilm_MockTest() {
        val mockProvider = UUID.randomUUID().toString()
        val filmExpected = expectedResult()
        val filmService = mock<FilmService> {
            on { getFilm(Mockito.anyString()) }
                .doReturn(filmExpected)
        }
        val filmController = FilmController()
        ReflectionTestUtils.setField(filmController, "filmService", filmService)
        val response = filmController.getFilm(mockProvider)
        Assertions.assertTrue(response.body?.provider == filmExpected.provider)
    }

    @Test
    fun testGetFilm_CinemaWorld() {
        val cinemaProvider = "cinemaworld"
        val filmResponse: BaseResponse<FilmResponse> = filmController.getFilm(cinemaProvider)
        Assertions.assertTrue(filmResponse.code == 200)
    }

    @Test
    fun testGetFilm_FilmWorld() {
        val cinemaProvider = "filmworld"
        val filmResponse: BaseResponse<FilmResponse> = filmController.getFilm(cinemaProvider)
        Assertions.assertTrue(filmResponse.code == 200)
    }

    @Test
    fun testGetFilm_WrongProvider() {
        val cinemaProvider = "filmworlds"
        Assertions.assertThrows(BadRequestException::class.java) { filmController.getFilm(cinemaProvider) }
    }

    @Test
    fun wiremockTest(){
        wireMockServer.stubFor(get(urlEqualTo("/abc")).willReturn(aResponse().withStatus(200)))
        val httpClient = HttpClients.createDefault()
        val request = HttpGet("http://localhost:8080/abc")
        val httpResponse = httpClient.execute(request)
        Assertions.assertEquals(httpResponse.statusLine.statusCode, 200)
    }


    private fun expectedResult(): FilmResponse {
        return FilmResponse(
            provider = "",
            movies = ArrayList()
        )
    }

    companion object {
        @JvmStatic
        @BeforeAll
        fun setUp(filmControllerTest: FilmControllerTest): Unit {
            filmControllerTest.wireMockServer = WireMockServer()
            filmControllerTest.wireMockServer.start()
        }
    }
}
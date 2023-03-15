package com.example.princetheater.controller

import com.example.princetheater.exception.BadRequestException
import com.example.princetheater.exception.InternalError
import com.example.princetheater.model.BaseResponse
import com.example.princetheater.model.FilmResponse
import com.example.princetheater.services.FilmService
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.util.ReflectionTestUtils
import java.util.*


@SpringBootTest
class FilmControllerTest {
    @Autowired
    private lateinit var filmController: FilmController

    private lateinit var wireMockServer: WireMockServer

    companion object {
        const val SUCCESS_CODE = 200
    }

//    init { //TODO implement wire test
//        val options = WireMockConfiguration().port(8081)
//
//        wireMockServer = WireMockServer(options)
//        wireMockServer.start()
//        wireMockServer.stubFor(get(urlEqualTo("/abc")).willReturn(aResponse().withStatus(400)))
//    }


    @Test
    fun testGetFilm_MockTest() {
        val mockProvider = UUID.randomUUID().toString()
        val filmExpected = filmSample()
        val filmService = mock<FilmService> {
            on { getFilm(Mockito.anyString()) }
                .doReturn(filmExpected)
        }
        val filmController = FilmController()
        ReflectionTestUtils.setField(filmController, "filmService", filmService)
        val response = filmController.getFilm(mockProvider)
        Assertions.assertEquals(response.code, SUCCESS_CODE)
    }

    @Test
    fun testGetFilm_CinemaWorld() {
        val cinemaProvider = "cinemaworld"
        val filmService = mock<FilmService> {
            on { getFilm(Mockito.anyString()) }
                .doReturn(filmCinemaWorldSample())
        }
        ReflectionTestUtils.setField(filmController, "filmService", filmService)
        val filmResponse: BaseResponse<FilmResponse> = filmController.getFilm(cinemaProvider)
        Assertions.assertEquals(filmResponse.code, SUCCESS_CODE)
        Assertions.assertEquals(filmResponse.body?.provider, filmCinemaWorldSample().provider)
    }

    @Test
    fun testGetFilm_FilmWorld() {
        val cinemaProvider = "filmworld"
        val filmService = mock<FilmService> {
            on { getFilm(Mockito.anyString()) }
                .doReturn(filmFilmWorldSample())
        }
        ReflectionTestUtils.setField(filmController, "filmService", filmService)
        val filmResponse: BaseResponse<FilmResponse> = filmController.getFilm(cinemaProvider)
        Assertions.assertEquals(filmResponse.code, SUCCESS_CODE)
        Assertions.assertEquals(filmResponse.body?.provider, filmFilmWorldSample().provider)
    }

    @Test
    fun testGetFilm_WrongProvider() {
        val wrongCinemaProvider = UUID.randomUUID().toString()
        val filmService = mock<FilmService> {
            on { getFilm(Mockito.anyString()) }
                .doThrow(BadRequestException("Can not recognize film provider"))
        }
        ReflectionTestUtils.setField(filmController, "filmService", filmService)
        val exception =
            Assertions.assertThrows(BadRequestException::class.java) { filmController.getFilm(wrongCinemaProvider) }
        Assertions.assertEquals(exception.message, "Can not recognize film provider")
    }

    @Test
    fun testGetFilm_TimeOut() {
        val wrongCinemaProvider = UUID.randomUUID().toString()
        val filmService = mock<FilmService> {
            on { getFilm(Mockito.anyString()) }
                .doThrow(InternalError("Time out exception when try to call API"))
        }
        ReflectionTestUtils.setField(filmController, "filmService", filmService)
        val exception =
            Assertions.assertThrows(InternalError::class.java) { filmController.getFilm(wrongCinemaProvider) }
        Assertions.assertTrue(exception.message!!.lowercase().contains("time out"))
    }

//    @Test //todo implementing...
//    fun wiremockTest() {
//        val httpClient = HttpClients.createDefault()
//        val request = HttpGet("http://abc.com/abc")
//        val httpResponse = httpClient.execute(request)
//        Assertions.assertEquals(httpResponse.statusLine.statusCode, 200)
//    }


    private fun filmSample(): FilmResponse {
        return FilmResponse(
            provider = "",
            movies = ArrayList()
        )
    }

    private fun filmCinemaWorldSample(): FilmResponse {
        return FilmResponse(
            provider = "cinemaworld",
            movies = ArrayList()
        )
    }

    private fun filmFilmWorldSample(): FilmResponse {
        return FilmResponse(
            provider = "filmworld",
            movies = ArrayList()
        )
    }
}
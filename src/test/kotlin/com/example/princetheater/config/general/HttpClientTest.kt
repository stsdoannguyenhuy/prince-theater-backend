package com.example.princetheater.config.general

import com.fasterxml.jackson.annotation.JsonProperty
import org.apache.http.message.BasicHeader
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.test.util.ReflectionTestUtils
import java.net.UnknownHostException
import java.util.*


class HttpClientTest {

    companion object {
        private const val apiKey = "Yr2636E6BTD3UCdleMkf7UEdqKnd9n361TQL9An7"
        private const val host = "https://challenge.lexicondigital.com.au/api/v2/cinemaworld/movies"
    }

    @Test
    fun getTest_AnyUrl() {
        val sampleUrl = "https://google.com"
        val responseTest = HttpClient().get(sampleUrl)
        Assertions.assertNotNull(responseTest)
    }

    @Test
    fun getTest_UnknownHost() {
        val randomHost: String = UUID.randomUUID().toString()
        Assertions.assertThrows(
            UnknownHostException::class.java
        ) { HttpClient().get("https://$randomHost.com") }
    }

    @Test
    fun getTest_CastToModel() {
        //given
        val httpClient = HttpClient()
        val objectMapper = Converter().provideObjectMapper()
        ReflectionTestUtils.setField(httpClient, "objectMapper", objectMapper)
        val headers = listOf(BasicHeader("x-api-key", apiKey))
        //when
        val responseGiven = httpClient.get(host, headers, FilmResponse::class.java)
        //then
        Assertions.assertNotNull(responseGiven.provider)
        Assertions.assertTrue(responseGiven.movies.size > 0)
        Assertions.assertNotNull(responseGiven.movies[0].id)
    }

    private data class FilmResponse(
        @JsonProperty(value = "Provider") val provider: String = "",
        @JsonProperty("Movies") val movies: MutableList<com.example.princetheater.model.Film> = ArrayList()
    )

    private data class Film(
        @JsonProperty(value = "ID") val id: String = "",
        @JsonProperty("Title") val title: String = "",
        @JsonProperty("Type") val type: String = "",
        @JsonProperty("Poster") val poster: String = "",
        @JsonProperty("Actors") val actors: String = "",
        @JsonProperty("Price") val price: Double = 0.0,
    )


}
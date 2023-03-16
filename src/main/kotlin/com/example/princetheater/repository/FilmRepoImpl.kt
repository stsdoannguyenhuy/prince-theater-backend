package com.example.princetheater.repository

import com.example.princetheater.common.HttpClient
import com.example.princetheater.model.FilmProvider
import com.example.princetheater.model.FilmResponse
import org.apache.http.message.BasicHeader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository

interface FilmRepository {
    fun getFilm(filmProvider: FilmProvider): FilmResponse;

}

@Repository
class FilmRepoImpl : FilmRepository {
    @Value("\${com.prince-theater.api-key}")
    private lateinit var apiKey: String

    @Autowired
    private lateinit var httpClient: HttpClient

    companion object {
        private val filmBaseUrl = "https://challenge.lexicondigital.com.au"
        private val filmApi = "/api/v2/{filmProvider}/movies"
    }

    override fun getFilm(filmProvider: FilmProvider): FilmResponse {
        val headers = listOf(BasicHeader("x-api-key", apiKey))
        return httpClient.get(filmBaseUrl + "/api/v2/${filmProvider.apiName}/movies", headers, FilmResponse::class.java)
    }
}
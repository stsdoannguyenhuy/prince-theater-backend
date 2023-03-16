package com.example.princetheater.repository

import com.example.princetheater.common.HttpClient
import com.example.princetheater.model.FilmProvider
import com.example.princetheater.model.FilmResponse
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.util.ReflectionTestUtils

@SpringBootTest
class FilmRepositoryTest {

    @Test
    fun testGetFilm() {
        val filmRepo = provideFilmRepo()
        val filmResponse = filmRepo.getFilm(FilmProvider.FILM_WORLD)
        Assertions.assertEquals(filmResponse.provider, filmSample().provider)
    }

    fun provideFilmRepo(): FilmRepository {
        val filmRepository = FilmRepoImpl()
        ReflectionTestUtils.setField(filmRepository, "apiKey", "ANY_API_KEY")
        val httpClient = mock<HttpClient> {
            on {
                get(
                    Mockito.anyString(), Mockito.anyList(), eq(FilmResponse::class.java)
                )
            }
                .doReturn(filmSample())
        }
        ReflectionTestUtils.setField(filmRepository, "httpClient", httpClient)
        return filmRepository
    }

    private fun filmSample(): FilmResponse {
        return FilmResponse(
            provider = "",
            movies = java.util.ArrayList()
        )
    }

}
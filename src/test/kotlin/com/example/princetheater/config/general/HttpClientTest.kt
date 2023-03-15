package com.example.princetheater.config.general

import com.example.princetheater.exception.InternalError
import com.fasterxml.jackson.annotation.JsonProperty
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.test.util.ReflectionTestUtils
import java.util.*


class HttpClientTest {

    companion object {
        const val SAMPLE_JSON_MODEL =
            "{\"Provider\":\"Cinema World\",\"Movies\":[{\"ID\":\"cw2488496\",\"Title\":\"Star Wars: Episode VII - The Force Awakens\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BOTAzODEzNDAzMl5BMl5BanBnXkFtZTgwMDU1MTgzNzE@._V1_SX300.jpg\",\"Actors\":\"Harrison Ford, Mark Hamill, Carrie Fisher, Adam Driver\",\"Price\":24.7},{\"ID\":\"cw2527336\",\"Title\":\"Star Wars: Episode VIII - The Last Jedi\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMjQ1MzcxNjg4N15BMl5BanBnXkFtZTgwNzgwMjY4MzI@._V1_SX300.jpg\",\"Actors\":\"Mark Hamill, Carrie Fisher, Adam Driver, Daisy Ridley\",\"Price\":24},{\"ID\":\"cw2527338\",\"Title\":\"Star Wars: Episode IX - The Rise of Skywalker\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMDljNTQ5ODItZmQwMy00M2ExLTljOTQtZTVjNGE2NTg0NGIxXkEyXkFqcGdeQXVyODkzNTgxMDg@._V1_SX300.jpg\",\"Actors\":\"Carrie Fisher, Mark Hamill, Adam Driver, Daisy Ridley\",\"Price\":23},{\"ID\":\"cw3748528\",\"Title\":\"Rogue One: A Star Wars Story\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMjEwMzMxODIzOV5BMl5BanBnXkFtZTgwNzg3OTAzMDI@._V1_SX300.jpg\",\"Actors\":\"Felicity Jones, Diego Luna, Alan Tudyk, Donnie Yen\",\"Price\":25},{\"ID\":\"cw3778644\",\"Title\":\"Solo: A Star Wars Story\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BOTM2NTI3NTc3Nl5BMl5BanBnXkFtZTgwNzM1OTQyNTM@._V1_SX300.jpg\",\"Actors\":\"Alden Ehrenreich, Joonas Suotamo, Woody Harrelson, Emilia Clarke\",\"Price\":24.5},{\"ID\":\"cw0076759\",\"Title\":\"Star Wars: Episode IV - A New Hope\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BNzg4MjQxNTQtZmI5My00YjMwLWJlMjUtMmJlY2U2ZWFlNzY1XkEyXkFqcGdeQXVyODk4OTc3MTY@._V1_SX300.jpg\",\"Actors\":\"Mark Hamill, Harrison Ford, Carrie Fisher, Peter Cushing\",\"Price\":25.5},{\"ID\":\"cw0080684\",\"Title\":\"Star Wars: Episode V - The Empire Strikes Back\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BYmU1NDRjNDgtMzhiMi00NjZmLTg5NGItZDNiZjU5NTU4OTE0XkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg\",\"Actors\":\"Mark Hamill, Harrison Ford, Carrie Fisher, Billy Dee Williams\",\"Price\":23},{\"ID\":\"cw0086190\",\"Title\":\"Star Wars: Episode VI - Return of the Jedi\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BOWZlMjFiYzgtMTUzNC00Y2IzLTk1NTMtZmNhMTczNTk0ODk1XkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1_SX300.jpg\",\"Actors\":\"Mark Hamill, Harrison Ford, Carrie Fisher, Billy Dee Williams\",\"Price\":24.2},{\"ID\":\"cw0120915\",\"Title\":\"Star Wars: Episode I - The Phantom Menace\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BYTRhNjcwNWQtMGJmMi00NmQyLWE2YzItODVmMTdjNWI0ZDA2XkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1_SX300.jpg\",\"Actors\":\"Liam Neeson, Ewan McGregor, Natalie Portman, Jake Lloyd\",\"Price\":26.4},{\"ID\":\"cw0121765\",\"Title\":\"Star Wars: Episode II - Attack of the Clones\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMDAzM2M0Y2UtZjRmZi00MzVlLTg4MjEtOTE3NzU5ZDVlMTU5XkEyXkFqcGdeQXVyNDUyOTg3Njg@._V1_SX300.jpg\",\"Actors\":\"Ewan McGregor, Natalie Portman, Hayden Christensen, Christopher Lee\",\"Price\":20.5},{\"ID\":\"cw0121766\",\"Title\":\"Star Wars: Episode III - Revenge of the Sith\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BNTc4MTc3NTQ5OF5BMl5BanBnXkFtZTcwOTg0NjI4NA@@._V1_SX300.jpg\",\"Actors\":\"Ewan McGregor, Natalie Portman, Hayden Christensen, Ian McDiarmid\",\"Price\":23}]}"
    }

    @Test
    fun getTest_UnknownHost() {
        val randomHost: String = UUID.randomUUID().toString()
        Assertions.assertThrows(
            InternalError::class.java
        ) { HttpClient().get("https://$randomHost.com") }
    }

    @Test
    fun getTest_CastToModel() {
        val httpClient = HttpClient()
        val objectMapper = Converter().provideObjectMapper()
        ReflectionTestUtils.setField(httpClient, "objectMapper", objectMapper)
        val mockHttpClient = Mockito.spy(httpClient)
        Mockito.doReturn(SAMPLE_JSON_MODEL).`when`(mockHttpClient).get(Mockito.anyString(), Mockito.anyList())
        //when
        val actual = mockHttpClient.get("", listOf(), FilmResponse::class.java)
        //then
        Assertions.assertNotNull(actual.provider)
        Assertions.assertTrue(actual.movies.size > 0)
        Assertions.assertNotNull(actual.movies[0].id)
    }

    private data class FilmResponse(
        @JsonProperty(value = "Provider") val provider: String = "",
        @JsonProperty("Movies") val movies: MutableList<Film> = ArrayList()
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
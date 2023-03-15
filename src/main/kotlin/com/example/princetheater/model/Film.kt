package com.example.princetheater.model

import com.example.princetheater.exception.BadRequestException
import com.fasterxml.jackson.annotation.JsonProperty

data class FilmResponse(
    @JsonProperty(value = "Provider") val provider: String,
    @JsonProperty("Movies") val movies: MutableList<Film>
)

data class Film(
    @JsonProperty(value = "ID") val id: String,
    @JsonProperty("Title") val title: String,
    @JsonProperty("Type") val type: String,
    @JsonProperty("Poster") val poster: String,
    @JsonProperty("Actors") val actors: String,
    @JsonProperty("Price") val price: Double,
) {
    private fun formatCurrency(number: Double): String {
        return "$$number"
    }

    var priceAsString: String = formatCurrency(price)
}

enum class FilmProvider(val apiName: String, val showName: String) {
    FILM_WORLD("filmworld", "Film World"),
    CINEMA_WORLD("cinemaworld", "Cinema World"),
    ;

    companion object {
        fun find(apiName: String): FilmProvider {
            return FilmProvider.values().firstOrNull { it.apiName == apiName }
                ?: throw BadRequestException("Can not recognize film provider")
        }
    }
}
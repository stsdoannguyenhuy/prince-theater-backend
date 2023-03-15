package com.example.princetheater.services

import com.example.princetheater.model.FilmProvider
import com.example.princetheater.model.FilmResponse
import com.example.princetheater.repository.FilmRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

interface FilmService {
    fun getFilm(provider: String): FilmResponse;
}

@Service
class FilmServiceImpl @Autowired constructor(private val filmRepository: FilmRepository) : FilmService {

    override fun getFilm(provider: String): FilmResponse {
        return filmRepository.getFilm(FilmProvider.find(provider))
    }
}
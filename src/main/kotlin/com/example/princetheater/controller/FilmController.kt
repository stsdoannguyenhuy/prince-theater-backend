package com.example.princetheater.controller

import com.example.princetheater.model.BaseResponse
import com.example.princetheater.model.FilmResponse
import com.example.princetheater.services.FilmService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/films")
class FilmController : BaseController() {
    @Autowired
    private lateinit var filmService: FilmService

    @GetMapping
    fun getFilm(@RequestParam provider: String): BaseResponse<FilmResponse> {
        val response = filmService.getFilm(provider)
        return BaseResponse(response)
    }

}
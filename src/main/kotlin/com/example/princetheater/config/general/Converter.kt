package com.example.princetheater.config.general

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component


@Component
class Converter {
    @Bean
    fun provideObjectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        return objectMapper
    }
}
package com.example.princetheater.common

import com.example.princetheater.common.Converter
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ConverterTest {
    @Test
    fun createObjectMapperTest() {
        val objectMapperGiven =  Converter().provideObjectMapper()
        val objectMapperSample = provideSampleObjectMapper()
        Assertions.assertEquals(objectMapperGiven.deserializationConfig.deserializationFeatures,objectMapperSample.deserializationConfig.deserializationFeatures)
    }

    private fun provideSampleObjectMapper():ObjectMapper{
        val objectMapper = ObjectMapper()
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        return objectMapper
    }
}
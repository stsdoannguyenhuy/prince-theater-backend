package com.example.princetheater.config

import com.example.princetheater.common.Converter
import jakarta.servlet.FilterChain
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.util.ReflectionTestUtils

class LoggingFilterTest {
    @Test
    fun testLoggingFilter() {
        val req = MockHttpServletRequest()
        val res = MockHttpServletResponse()
        val chain = FilterChain { _, _ -> }
        val loggingFilter = LoggingFilter()
        val objectMapper = Converter().provideObjectMapper()
        ReflectionTestUtils.setField(loggingFilter, "objectMapper", objectMapper)
        loggingFilter.doFilter(req, res, chain)
        Assertions.assertTrue(true)
    }
}
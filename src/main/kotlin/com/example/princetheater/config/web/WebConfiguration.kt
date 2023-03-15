package com.example.princetheater.config.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebSecurity
class WebConfiguration : WebMvcConfigurer {

    @Bean
    fun configurePath(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests { it.requestMatchers("/**").permitAll() }
        return http.build()
    }
}
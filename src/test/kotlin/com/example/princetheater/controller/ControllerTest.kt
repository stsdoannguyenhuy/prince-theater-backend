//package com.example.princetheater.controller
//
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
//import org.springframework.test.web.servlet.MockMvc
//
//@WebMvcTest
//class ControllerTest {
//    @Autowired
//    private lateinit var mockMvc: MockMvc
//    @Test
//    fun testBaseController(){
//        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
//            .andExpect(content().string(containsString("Hello, World")));
//    }
//}
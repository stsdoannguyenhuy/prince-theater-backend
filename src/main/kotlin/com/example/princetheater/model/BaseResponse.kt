package com.example.princetheater.model

import org.apache.http.HttpStatus

data class BaseResponse<T>(val code: Int = HttpStatus.SC_OK, val message: String? = "", val body: T? = null) {
    constructor(code: Int,message: String?):this(code,message,null)
    constructor(body: T?):this(HttpStatus.SC_OK,"",body)
}
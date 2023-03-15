package com.example.princetheater.exception

import com.example.princetheater.model.BaseResponse
import org.apache.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.NoHandlerFoundException

@ControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(
        BadRequestException::class,
        org.springframework.web.bind.MissingServletRequestParameterException::class
    )
    fun handleBadRequest(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        val status = HttpStatus.SC_BAD_REQUEST;
        return ResponseEntity.status(status)
            .body(BaseResponse<Any>(status, ex.message))
    }

    @ExceptionHandler(
        InternalError::class,
        Exception::class
    )
    fun handleUnknownError(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        ex.printStackTrace()
        val status = HttpStatus.SC_INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status)
            .body(BaseResponse<Any>(status, ex.message))
    }

    @ExceptionHandler(
        NoHandlerFoundException::class
    )
    fun handleNoHandlerFound(e: NoHandlerFoundException, request: WebRequest?): ResponseEntity<Any> {
        val status = HttpStatus.SC_NOT_FOUND;
        return ResponseEntity.status(status)
            .body(BaseResponse<Any>(status, e.message))
    }

}
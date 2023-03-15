package com.example.princetheater.exception

abstract class PrinceTheaterException : RuntimeException {
    constructor(message: String) : super(message)
    constructor(message: String, error: Throwable) : super(message, error)
}

class BadRequestException : PrinceTheaterException {
    constructor(message: String) : super(message)
    constructor(message: String, error: Throwable) : super(message, error)
}

class InternalError : PrinceTheaterException {
    constructor(message: String) : super(message)
    constructor(message: String, error: Throwable) : super(message, error)
}
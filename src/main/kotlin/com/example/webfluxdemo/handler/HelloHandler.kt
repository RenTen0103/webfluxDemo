package com.example.webfluxdemo.handler

import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class HelloHandler {
    suspend fun hello(request: ServerRequest): ServerResponse {
        val userid = request.exchange().getAttribute<String>("userid")
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).bodyValueAndAwait("hello${userid}")
    }
}
package com.example.webfluxdemo.handler

import com.example.webfluxdemo.entity.dto.AccountInfo
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class HelloHandler {
    suspend fun hello(request: ServerRequest): ServerResponse {
        val user = request.exchange().getAttribute<AccountInfo>("account")
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
            .bodyValueAndAwait("id:${user?.id}\nusername:${user?.username}\nrole:${user?.role}")
    }
}
package com.example.webfluxdemo.domain

import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait


class ResponseResult<T>(val code: Int = 0, val message: String = "", val data: T) {
    suspend fun getResponse() = ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(this)

    companion object {
        @JvmOverloads
        suspend fun error(httpStatusCode: Int, message: String, code: Int = 900) =
            ServerResponse.status(httpStatusCode).contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(
                ResponseResult(code, message, "")
            )
    }

}


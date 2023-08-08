package com.example.webfluxdemo.filter

import com.example.webfluxdemo.domain.ResponseResult
import com.example.webfluxdemo.utils.JwtUtils
import io.jsonwebtoken.security.SignatureException
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

object AuthFilter {
    suspend fun doFilter(request: ServerRequest, next: suspend (ServerRequest) -> ServerResponse): ServerResponse {
        val token = request.headers().header("Authorization").run {
            if (this.size != 0) {
                this[0]
            } else null
        }


        return if (token == null) {
            ResponseResult.error(401, "认证失败")
        } else {
            try {
                val userid = JwtUtils.parseJwt(JwtUtils.prefix(token))
                request.exchange().attributes["userid"] = userid
                next(request)
            } catch (e: SignatureException) {
                ResponseResult.error(401, "认证失败", 101)
            }
        }

    }

}
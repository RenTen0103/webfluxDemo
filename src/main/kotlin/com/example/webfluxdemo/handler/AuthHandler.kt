package com.example.webfluxdemo.handler

import com.example.webfluxdemo.entity.domain.ResponseResult
import com.example.webfluxdemo.entity.vo.Login
import com.example.webfluxdemo.entity.dto.LoginSuccessResponse
import com.example.webfluxdemo.entity.vo.Register
import com.example.webfluxdemo.entity.dto.RegisterSuccessResponse
import com.example.webfluxdemo.service.AuthService
import com.example.webfluxdemo.utils.JwtUtils
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

@Component
class AuthHandler(
    @Autowired val server: AuthService
) {


    suspend fun login(request: ServerRequest): ServerResponse {
        val data = request.bodyToFlux(Login::class.java).awaitFirst()
        server.login(data).let {
            if (it == null)
                return ResponseResult.error(401, "用户名或密码错误")
            else return ResponseResult(code = 1, data = LoginSuccessResponse(JwtUtils.createJwt(it))).getResponse()
        }
    }

    suspend fun register(request: ServerRequest): ServerResponse {
        val data = request.bodyToFlux(Register::class.java).awaitFirst()
        return if (server.checkUsernameAvailable(data.username)) {
            val account = server.addAccount(data).awaitFirst()
            ResponseResult(code = 1, data = RegisterSuccessResponse(account.id, account.username)).getResponse()
        } else ResponseResult.error(409, "用户名已被注册")
    }
}
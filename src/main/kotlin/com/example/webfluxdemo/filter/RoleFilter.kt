package com.example.webfluxdemo.filter

import com.example.webfluxdemo.common.Role
import com.example.webfluxdemo.entity.domain.ResponseResult
import com.example.webfluxdemo.entity.dto.AccountInfo
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

private typealias Filter = suspend (ServerRequest, suspend (ServerRequest) -> ServerResponse) -> ServerResponse

object RoleFilter {
    fun roleFilterFactory(roles: List<Role>): Filter {
        return { request: ServerRequest, next: suspend (ServerRequest) -> ServerResponse ->
            val accountInfo = request.exchange().getAttribute<AccountInfo>("account")
            if (accountInfo == null || !roles.contains(accountInfo.role)) {
                ResponseResult.error(401, "认证失败", 101)
            } else {
                next(request)
            }
        }
    }
}

package com.example.webfluxdemo.filter

import com.example.webfluxdemo.common.Role
import org.springframework.web.reactive.function.server.CoRouterFunctionDsl


fun CoRouterFunctionDsl.hasRole(vararg role: Role) {
    this.filter(RoleFilter.roleFilterFactory(role.toList()))
}

fun CoRouterFunctionDsl.enableJwtAuth() {
    this.filter(AuthFilter::doFilter)
}
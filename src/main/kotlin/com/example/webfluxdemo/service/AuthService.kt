package com.example.webfluxdemo.service

import com.example.webfluxdemo.dao.Account
import com.example.webfluxdemo.dto.Login
import com.example.webfluxdemo.dto.Register
import reactor.core.publisher.Mono

interface AuthService {
    suspend fun addAccount(register: Register): Mono<Account>
    suspend fun checkUsernameAvailable(username: String): Boolean
    suspend fun login(login: Login): Account?

}
package com.example.webfluxdemo.repository

import com.example.webfluxdemo.dao.Account
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface AccountRepository : ReactiveCrudRepository<Account, String> {
    fun findAccountByUsername(username: String): Mono<Account>
}
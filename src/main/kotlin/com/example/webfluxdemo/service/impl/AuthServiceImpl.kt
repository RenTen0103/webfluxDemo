package com.example.webfluxdemo.service.impl

import com.example.webfluxdemo.dao.Account
import com.example.webfluxdemo.dto.Login
import com.example.webfluxdemo.dto.Register
import com.example.webfluxdemo.repository.AccountRepository
import com.example.webfluxdemo.service.AuthService
import com.example.webfluxdemo.utils.IdUtils
import com.example.webfluxdemo.utils.Md5Utils
import com.example.webfluxdemo.utils.md5
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AuthServiceImpl(
    @Autowired val accountRepository: AccountRepository,
) : AuthService {
    override suspend fun addAccount(register: Register): Mono<Account> {
        return accountRepository.save(Account(IdUtils.nextID(), register.username, Md5Utils.encrypt(register.password)))
    }

    override suspend fun checkUsernameAvailable(username: String): Boolean {
        val user = accountRepository.findAccountByUsername(username).awaitFirstOrNull()
        return user == null
    }

    override suspend fun login(login: Login): Account? {
        val user = accountRepository.findAccountByUsername(login.username).awaitFirstOrNull()
        return if (user != null && Md5Utils.match(login.password, user.password)) {
            user
        } else null
    }
}
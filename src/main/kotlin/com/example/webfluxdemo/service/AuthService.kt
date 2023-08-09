package com.example.webfluxdemo.service

import com.example.webfluxdemo.common.Role
import com.example.webfluxdemo.entity.dao.Account
import com.example.webfluxdemo.entity.vo.Login
import com.example.webfluxdemo.entity.vo.Register
import com.example.webfluxdemo.repository.AccountRepository
import com.example.webfluxdemo.utils.IdUtils
import com.example.webfluxdemo.utils.Md5Utils
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AuthService(
    @Autowired val accountRepository: AccountRepository,
) {

    val logger: Logger = LoggerFactory.getLogger(AuthService::class.java)


    suspend fun addAccount(register: Register): Mono<Account> {
        logger.info("new account created")
        return accountRepository.save(
            Account(
                IdUtils.nextID(),
                register.username,
                Md5Utils.encrypt(register.password),
                Role.User
            )
        )
    }

    suspend fun checkUsernameAvailable(username: String): Boolean {
        val user = accountRepository.findAccountByUsername(username).awaitFirstOrNull()
        return user == null
    }

    suspend fun login(login: Login): Account? {
        val user = accountRepository.findAccountByUsername(login.username).awaitFirstOrNull()
        return if (user != null && Md5Utils.match(login.password, user.password)) {
            user
        } else null
    }
}
package com.example.webfluxdemo.utils

import com.example.webfluxdemo.common.Role
import com.example.webfluxdemo.entity.dao.Account
import org.junit.jupiter.api.Test
import com.example.webfluxdemo.utils.JwtUtils.createJwt
import com.example.webfluxdemo.utils.JwtUtils.parseJwt
import io.jsonwebtoken.Jwts

object JwtTest {
    @Test
    fun jwtTest() {
        val subject = Account("test", "renren", "test", Role.User)
        val jwtString = createJwt(subject)
        val info = parseJwt(jwtString)
        println(info)
    }

    @Test
    fun subString() {
        val jwtString = "Bear 1234567890"
        jwtString.startsWith("Bear ")
        println(jwtString.subSequence(5, jwtString.length))
    }
}
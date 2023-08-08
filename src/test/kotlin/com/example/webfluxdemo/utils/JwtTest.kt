package com.example.webfluxdemo.utils

import org.junit.jupiter.api.Test
import com.example.webfluxdemo.utils.JwtUtils.createJwt
import com.example.webfluxdemo.utils.JwtUtils.parseJwt
import io.jsonwebtoken.Jwts

object JwtTest {
    @Test
    fun jwtTest() {
        val jwtString = createJwt("join")
        val paresString = parseJwt(jwtString)
        println(paresString)
        println("join" == paresString)
    }

    @Test
    fun subString() {
        val jwtString = "Bear 1234567890"
        jwtString.startsWith("Bear ")
        println(jwtString.subSequence(5, jwtString.length))
    }
}
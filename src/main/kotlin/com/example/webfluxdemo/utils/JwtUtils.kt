package com.example.webfluxdemo.utils

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import java.nio.charset.StandardCharsets
import java.security.Key
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import java.util.concurrent.TimeUnit


object JwtUtils {
    private const val secret = "#####webflux-demo@sskey#####"
    private val key: Key = Keys.hmacShaKeyFor(Base64.getEncoder().encode(secret.toByteArray(StandardCharsets.UTF_8)))
    val LONG_TTL = TimeUnit.DAYS.toSeconds(7)
    fun createJwt(subject: String): String {
        val now = LocalDateTime.now().atZone(ZoneId.systemDefault())
        val expiration: ZonedDateTime = now.plusSeconds(LONG_TTL)

        return Jwts.builder()
            .setExpiration(Date.from(expiration.toInstant()))
            .setIssuedAt(Date.from(now.toInstant()))
            .setSubject(subject)
            .signWith(key)
            .compact()
    }

    fun parseJwt(jwtString: String): Any {
        jwtString.subSequence(7, jwtString.length)
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtString).body.subject
    }

    fun prefix(jwtString: String): String {
        return if (jwtString.startsWith("Bear ")) {
            jwtString.substring(5, jwtString.length)
        } else ""
    }
}
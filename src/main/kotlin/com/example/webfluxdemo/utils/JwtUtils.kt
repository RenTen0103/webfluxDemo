package com.example.webfluxdemo.utils

import com.example.webfluxdemo.common.Role
import com.example.webfluxdemo.entity.dao.Account
import com.example.webfluxdemo.entity.dto.AccountInfo
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
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
    private val LONG_TTL = TimeUnit.DAYS.toSeconds(7)
    private val ROLE_FILED = "ROLE"
    fun createJwt(subject: Account): String {
        val now = LocalDateTime.now().atZone(ZoneId.systemDefault())
        val expiration: ZonedDateTime = now.plusSeconds(LONG_TTL)

        return Jwts.builder()
            .setExpiration(Date.from(expiration.toInstant()))
            .claim(ROLE_FILED, subject.role)
            .setId(subject.id)
            .setIssuedAt(Date.from(now.toInstant()))
            .setSubject(subject.username)
            .signWith(key)
            .compact()
    }

    fun parseJwt(token: String): AccountInfo {
        val claims = verify(token).body
        val username = claims.subject
        val id = claims.id
        val role = Role.valueOf(claims[ROLE_FILED].toString())
        return AccountInfo(id, username, role)
    }

    fun prefix(jwtString: String): String {
        return if (jwtString.startsWith("Bear ")) {
            jwtString.substring(5, jwtString.length)
        } else ""
    }

    private fun verify(token: String): Jws<Claims> {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
    }
}
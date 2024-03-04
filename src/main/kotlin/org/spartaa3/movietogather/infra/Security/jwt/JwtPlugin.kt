package org.spartaa3.movietogather.infra.Security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import java.util.*

@Component
class JwtPlugin (
    @Value("\${auth.jwt.issuer}") private val issuer: String,
    @Value("\${auth.jwt.secret}") private val secret: String,
    @Value("\${auth.jwt.accessTokenExpirationHour}") private val accessTokenExpirationHour: Long,
    ) {

    fun validateToken(jwt: String): Jws<Claims> {

        val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt)
    }

    fun generateToken(memberId: Long, email: String, role: String): String {
        val duration = Duration.ofHours(accessTokenExpirationHour)

        val claims: Claims = Jwts.claims()
            .add("email", email)
            .add("role", role)
            .build()

        val now  = Instant.now()
        val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))

        return Jwts.builder()
            .subject(memberId.toString())
            .issuer(issuer)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(duration)))
            .claims(claims)
            .signWith(key)
            .compact()
    }
}
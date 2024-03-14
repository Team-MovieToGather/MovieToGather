package org.spartaa3.movietogather.infra.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.spartaa3.movietogather.domain.member.oauth2.OAuth2Provider
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import java.util.*

@Component
class JwtPlugin(
    @Value("\${AUTH_JWT_ISSUER}") private val issuer: String,
    @Value("\${AUTH_JWT_SECRET}") private val secret: String,
    @Value("\${AUTH_JWT_ACCESSTOKENEXPIRATIONHOUR}") private val accessTokenExpirationHour: Long,
    @Value("\${AUTH_JWT_REFRESHTOKENEXPIRATIONHOUR}") private val refreshTokenExpirationHour: Long
) {

    companion object {
        const val BEARER_PREFIX = "Bearer"
    }

    fun validateToken(jwt: String): Result<Jws<Claims>> {
        return kotlin.runCatching {
            val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
            Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(jwt)
        }
    }

    fun createToken(oAuth2User: OAuth2User, id: String, email: String, oAuthType: OAuth2Provider): JwtDto {
        val now = Instant.now()
        val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
        val expirationTime  = Date(Date().time + accessTokenExpirationHour)
        val expirationPeriod = Duration.ofHours(1)
        val email: String? = when (oAuthType.name) {
            "GOOGLE" -> oAuth2User.getAttribute<String>("email")
            "NAVER" -> oAuth2User.getAttribute<Map<*, *>>("response")?.get("email") as? String
            "KAKAO" -> oAuth2User.getAttribute<Map<*, *>>("kakao_account")?.get("email") as? String
            else -> null
        }

        val claims: Claims = Jwts.claims()
            .add(mapOf("email" to email, "oauthType" to oAuthType))
            .build()

        val accessToken = Jwts.builder()
            .subject(id)
            .issuer(issuer)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(expirationPeriod)))
            .claims(claims)
            .signWith(key)
            .compact()

        val refreshToken = Jwts.builder()
            .expiration(Date(Date().time+refreshTokenExpirationHour))
            .signWith(key)
            .compact()

        return JwtDto(
            grantType = BEARER_PREFIX,
            accessToken = accessToken,
            refreshToken = refreshToken,
            accessTokenExpires = expirationTime.time
        )
    }
}
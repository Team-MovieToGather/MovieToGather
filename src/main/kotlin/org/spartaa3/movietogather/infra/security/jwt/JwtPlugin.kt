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
    @Value("\${auth.jwt.issuer}") private val issuer: String,
    @Value("\${auth.jwt.secret}") private val secret: String,
    @Value("\${auth.jwt.accesstokenexpirationhour}") private val accessTokenExpirationHour: Long,
    @Value("\${auth.jwt.refreshtokenexpirationhour}") private val refreshTokenExpirationHour: Long
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
    fun refreshAccessToken(oAuth2User: OAuth2User, refreshToken: String): Jws<Claims> {
        return kotlin.runCatching {
            validateToken(refreshToken).onSuccess { refreshTokenClaims ->
                val email = refreshTokenClaims.body["email"] as String
                val role = refreshTokenClaims.body["role"] as String
                val oAuthType = refreshTokenClaims.body["oAuthType"] as String

                createToken(oAuth2User, email, role, OAuth2Provider.valueOf(oAuthType))
            }.getOrThrow()
        }.getOrElse {
            throw RefreshTokenExpiredException("Refresh token has expired.")
        }
    }

}
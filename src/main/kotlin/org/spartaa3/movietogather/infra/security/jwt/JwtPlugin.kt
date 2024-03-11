package org.spartaa3.movietogather.infra.security.jwt

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
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
) {

    private val log = LoggerFactory.getLogger(JwtPlugin::class.java)
    private val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
            true
        } catch (ex: UnsupportedJwtException) {
            log.error("JWT is not valid")
            false
        } catch (ex: MalformedJwtException) {
            log.error("JWT is not valid")
            false
        } catch (ex: SignatureException) {
            log.error("JWT signature validation fails")
            false
        } catch (ex: ExpiredJwtException) {
            log.error("JWT is expired")
            false
        } catch (ex: IllegalArgumentException) {
            log.error("JWT is null or empty or only whitespace")
            false
        } catch (ex: Exception) {
            log.error("JWT validation fails", ex)
            false
        }
    }

    fun createToken(authentication: Authentication): String {
        val currentDate = Date()
        val expiryDate = Date(currentDate.time + accessTokenExpirationHour * 3600000) // Convert hours to milliseconds

        return Jwts.builder()
            .issuer(issuer)
            .subject(authentication.name)
            .issuedAt(currentDate)
            .expiration(expiryDate)
            .signWith(key)
            .compact()
    }

    fun getAuthentication(token: String?): Authentication {
        val claims: Claims = Jwts.parser()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()

        val user: UserDetails = User(claims.subject, "", emptyList())

        return UsernamePasswordAuthenticationToken(user, "", emptyList())
    }

}
package org.spartaa3.movietogather.infra.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.spartaa3.movietogather.domain.member.entity.Member
import org.spartaa3.movietogather.domain.member.entity.MemberToken
import org.spartaa3.movietogather.domain.member.repository.MemberRepository
import org.spartaa3.movietogather.domain.member.repository.TokenRepository
import org.spartaa3.movietogather.global.exception.TokenNotFoundException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import java.util.*

@Component
class JwtPlugin(
    @Value("\${auth.jwt.issuer}") private val issuer: String,
    @Value("\${auth.jwt.secret}") private val secret: String,
    @Value("\${auth.jwt.accessTokenExpirationHour}") private val accessTokenExpirationHour: Long,
    @Value("\${auth.jwt.refreshTokenExpirationHour}") private val refreshTokenExpirationHour: Long,
    private val memberRepository: MemberRepository,
    private val tokenRepository: TokenRepository
) {
    fun generateAccessToken(subject: String, email: String, role: String): String {
        return generateToken(subject, email, role, Duration.ofHours(accessTokenExpirationHour))
    }

    fun generateRefreshToken(subject: String, email: String, role: String): String {
        return generateToken(subject, email, role, Duration.ofHours(refreshTokenExpirationHour))
    }

    private fun generateToken(subject: String, email: String, role: String, expirationPeriod: Duration): String {
        val claims: Claims = Jwts.claims()
            .add(mapOf("role" to role, "email" to email))
            .build()
        val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
        val now = Instant.now()
        return Jwts.builder()
            .subject(subject)
            .issuer(issuer)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(expirationPeriod)))
            .claims(claims)
            .signWith(key)
            .compact()
    }

    fun validateToken(jwt: String): Result<Jws<Claims>> {
        return kotlin.runCatching {
            val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
            Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt)
        }
    }

    @Transactional
    fun storeToken(member: Member, refreshToken: String) {
        tokenRepository.save(MemberToken(member, refreshToken))
    }

    @Transactional
    fun deleteToken(userPrincipal: UserPrincipal) {
        val member = memberRepository.findByEmail(userPrincipal.email)
        val refreshToken = tokenRepository.findByMember(member) ?: throw TokenNotFoundException("refreshToken")
        tokenRepository.delete(refreshToken)
    }

}
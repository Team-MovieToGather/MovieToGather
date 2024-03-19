package org.spartaa3.movietogather.infra.security.jwt

import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.spartaa3.movietogather.global.cookie.CookieUtils
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtPlugin: JwtPlugin
): OncePerRequestFilter() {
    companion object{
        private val BEARER_PATTERN = Regex("^Bearer (.+?)$")
    }
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jwt = request.getBearerToken()

        if (jwt != null) {
            try {
                jwtPlugin.validateToken(jwt).onSuccess { jwtClaims ->
                    val email = jwtClaims.body["email"] as String
                    val role = jwtClaims.body["role"] as String
                    val oauthType = jwtClaims.body["oauthType"] as String

                    val principal = UserPrincipal(email, setOf(role), oauthType)
                    val authentication = JwtAuthenticationToken(
                        principal,
                        WebAuthenticationDetailsSource().buildDetails(request)
                    )
                    SecurityContextHolder.getContext().authentication = authentication
                }.onFailure { throwable ->
                    if (throwable is ExpiredJwtException) {
                        val refreshTokenCookie = CookieUtils.getCookie(request, "refreshToken")

                        if (refreshTokenCookie.isPresent) {
                            val refreshToken = refreshTokenCookie.get().value
                            try {
                                val auth = SecurityContextHolder.getContext().authentication
                                val oAuth2User = if (auth is OAuth2AuthenticationToken) {
                                    auth.principal
                                } else {
                                    null
                                }
                                if (oAuth2User != null) {
                                    val newAccessToken = jwtPlugin.refreshAccessToken(oAuth2User, refreshToken)
                                    val email = newAccessToken.body["email"] as String
                                    val role = newAccessToken.body["role"] as String
                                    val oauthType = newAccessToken.body["oauthType"] as String

                                    val principal = UserPrincipal(email, setOf(role), oauthType)
                                    val authentication = JwtAuthenticationToken(
                                        principal,
                                        WebAuthenticationDetailsSource().buildDetails(request)
                                    )
                                    SecurityContextHolder.getContext().authentication = authentication
                                } else {
                                    response.sendRedirect("/login")
                                    return
                                }
                            } catch (e: RefreshTokenExpiredException) {
                                response.sendRedirect("/login")
                                return
                            }
                        } else {
                            response.sendRedirect("/login")
                            return
                        }
                    }
                }
            } catch (e: Exception) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
                return
            }
        }
        filterChain.doFilter(request, response)
    }
    private fun HttpServletRequest.getBearerToken(): String? {
        val headerValue = this.getHeader(HttpHeaders.AUTHORIZATION) ?: return null
        return BEARER_PATTERN.find(headerValue)?.groupValues?.get(1)
    }
}
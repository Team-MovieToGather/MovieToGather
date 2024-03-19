package org.spartaa3.movietogather.infra.security.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.spartaa3.movietogather.global.cookie.CookieUtils
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
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
            jwtPlugin.validateToken(jwt)
                .onSuccess {
                    val email = it.payload.get("email", String::class.java)
                    val role = it.payload.get("role", String::class.java)
                    val oauthType = it.payload.get("oauthType", String::class.java)
                    val principal = UserPrincipal(
                        email = email,
                        roles = setOf(role),
                        oauthType = oauthType
                    )

                    val authentication = JwtAuthenticationToken(
                        principal = principal,
                        details = WebAuthenticationDetailsSource().buildDetails(request)
                    )
                    SecurityContextHolder.getContext().authentication = authentication
                }
        }
        filterChain.doFilter(request, response)
    }
    private fun HttpServletRequest.getBearerToken(): String? {
        val headerValue = this.getHeader(HttpHeaders.AUTHORIZATION) ?: return null
        return BEARER_PATTERN.find(headerValue)?.groupValues?.get(1)
    }
}
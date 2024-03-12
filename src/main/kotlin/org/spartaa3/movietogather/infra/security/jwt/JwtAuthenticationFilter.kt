package org.spartaa3.movietogather.infra.security.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class JwtAuthenticationFilter(
    private val jwtPlugin: JwtPlugin
) : OncePerRequestFilter() {

    companion object {
        private val BEARER_PATTERN = Regex("^Bearer (.+?)$")
        const val AUTHORIZATION_HEADER = "Authorization"
        const val BEARER_PREFIX = "Bearer "
    }

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
//        쿠값키가을 고 지와겠야지?
//        서버에서 get-cookie릃 할수도있어
// 쿠키에는 토큰이 들어가있다!

        val token = resolveToken(request)

        if (StringUtils.hasText(token) && token?.let { jwtPlugin.validateToken(it) } == true) {
            val authentication = jwtPlugin.getAuthentication(token)
            SecurityContextHolder.getContext().authentication = authentication
            response.setHeader(AUTHORIZATION_HEADER, "$BEARER_PREFIX$token")
        }

        filterChain.doFilter(request, response)
    }
    private fun resolveToken(request: HttpServletRequest): String? {
        val token = request.getHeader(AUTHORIZATION_HEADER)

        if (StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
            return token.substring(BEARER_PREFIX.length)
        }

        return null
    }
}
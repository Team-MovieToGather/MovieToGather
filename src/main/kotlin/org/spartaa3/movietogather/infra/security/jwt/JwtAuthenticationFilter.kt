package org.spartaa3.movietogather.infra.security.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.spartaa3.movietogather.domain.member.entity.Member
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class JwtAuthenticationFilter(
    private val jwtPlugin: JwtPlugin
) : OncePerRequestFilter() {

    companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
        const val BEARER_PREFIX = "Bearer "
    }

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorization = request.getHeader(HttpHeaders.AUTHORIZATION)
        val jwt = try {
            BEARER_PATTERN.matchEntire(authorization)?.groupValues?.get(1)
        } catch (e: Exception) {
            null
        }

        if (jwt != null) {

            val claims = jwtPlugin.validateToken(jwt)
            val memberId = claims.payload.subject.toLong()
            val email = claims.payload.get("email", String::class.java)
            val role = claims.payload.get("role", String::class.java)

            val userPrincipal = UserPrincipal(memberId, email, Member.MemberRole.valueOf(role))

            val authentication = JwtAuthenticationToken(
                principal = userPrincipal,
                details = WebAuthenticationDetailsSource().buildDetails(request)
            )

            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }
}
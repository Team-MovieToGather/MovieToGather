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
        CookieUtils.getCookie(request, "refreshToken")

        //home?access_teken=asdflkl;dfkjdfsa;lkjfdsa;kjdfsa;lk
        //프론트에서 access_token 받아서  -> vue에 local저장소에 저장
        //api호출시에 저장된 access_token을 header에 넣어서 api호출--이게 뭔지 알겠지?

        //어느순간에 access-token이 만료일이 다되거나, 로그아웃해서 access_token이 지워졌어(아마 로그인하면, local저장소에서 access_token지워야겠지?)
//        if(access-token의 만료일이 다되었는가? || access_token이 있는가? )
//            if(리프레시 토큰이 있는가?){
//                DB에서 리프레시 토큰에서 이메일 가와져서 유저테이블에 조회 = 리프레시 토큰과 같다면 에세스 토큰 다시 만들어서
//                //home?access_teken=asdflkl;dfkjdfsa;lkjfdsa;kjdfsa;lk
//                //프론트에서 access_token 받아서  -> vue에 local저장소에 저장
//            }
//            else{
//                로그인으로 이동(redirect로???모르겠다.)
//        }


        //

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
package org.spartaa3.movietogather.domain.member.service

import jakarta.servlet.http.HttpServletResponse
import org.spartaa3.movietogather.domain.member.dto.LoginResponse
import org.spartaa3.movietogather.domain.member.oauth2.OAuth2Provider
import org.spartaa3.movietogather.global.cookie.CookieUtils
import org.spartaa3.movietogather.infra.security.jwt.JwtPlugin
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class OAuth2LoginService(
    private val oAuth2ClientService: OAuth2ClientService,
    private val oAuth2MemberService: OAuth2MemberService,
    private val jwtPlugin: JwtPlugin,
    @Value("\${server.reactive.session.cookie.max-age}") private val cookieExpirationTime: Int,
) {

    fun login(
        provider: OAuth2Provider,
        response: HttpServletResponse,
        authorizationCode: String
    ): LoginResponse {

        val memberInfo = oAuth2ClientService.login(provider, authorizationCode)

        val member = oAuth2MemberService.registerIfAbsent(memberInfo)

        val refreshToken = jwtPlugin.generateRefreshToken(member.id.toString(), member.email, member.role.name)

        CookieUtils.addCookie(response, "refreshToken", refreshToken, cookieExpirationTime)
        jwtPlugin.storeToken(member, refreshToken)

        val accessToken = jwtPlugin.generateAccessToken(member.id.toString(), member.email, member.role.name)

        return LoginResponse(accessToken)

    }
}
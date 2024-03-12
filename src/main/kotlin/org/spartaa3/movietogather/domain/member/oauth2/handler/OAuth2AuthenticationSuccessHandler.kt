package org.spartaa3.movietogather.domain.member.oauth2.handler

import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.spartaa3.movietogather.domain.member.service.TokenServiceImpl
import org.spartaa3.movietogather.global.cookie.CookieUtils
import org.spartaa3.movietogather.global.cookie.HttpCookieOAuth2AuthorizationRequestRepository
import org.spartaa3.movietogather.infra.security.jwt.JwtPlugin
import org.spartaa3.movietogather.infra.security.jwt.OAuth2UserPrincipal
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import java.io.IOException
import java.util.*

@Component
class OAuth2AuthenticationSuccessHandler(
    private val httpCookieOAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository,
    private val jwtPlugin: JwtPlugin,
    private val tokenService: TokenServiceImpl
) : SimpleUrlAuthenticationSuccessHandler() {


    val log = KotlinLogging.logger {}

    @Throws(IOException::class)
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val targetUrl: String = determineTargetUrl(request, response, authentication)

        if (response.isCommitted) {
            logger.debug("Response has already been committed. Unable to redirect to $targetUrl")
            return
        }

        clearAuthenticationAttributes(request, response)
        redirectStrategy.sendRedirect(request, response, targetUrl)
    }

    override fun determineTargetUrl(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ): String {
        val redirectUri: Optional<String> = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME).map { it.value }
        val targetUrl: String = redirectUri.orElse("/home")
        val principal: OAuth2UserPrincipal? = getOAuth2UserPrincipal(authentication)

        if (principal == null) {
            return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("error", "Login failed")
                .build().toUriString()
        }


        log.info(
            "email={}, name={}, nickname={}, accessToken={}",
            principal.memberInfo.getEmail(),
            principal.memberInfo.getName(),
            principal.memberInfo.getNickname(),
            principal.memberInfo.getAccessToken()
        )



        val accessToken = jwtPlugin.createToken(authentication)
        val refreshToken = jwtPlugin.createRefreshToken(authentication)

        val refreshTokenCookieName = generateUniqueCookieName()

        val refreshTokenCookie = Cookie(refreshTokenCookieName, refreshToken)
        refreshTokenCookie.isHttpOnly = true
        refreshTokenCookie.path = "/"
        response.addCookie(refreshTokenCookie)

        tokenService.saveRefreshToken(authentication.name, refreshToken)

        return UriComponentsBuilder.fromUriString(targetUrl)
//            .queryParam("access_token", accessToken)
//            .queryParam("refresh_token", refreshTokenCookieName)//존나 멍청했다 왜 헤더만 살피고 uri를 안보니 //논리적으로 생각하자//천천히 뜯어보고//한가지 생각에 매몰되면 다른걸 보지 못한다
            .build().toUriString()

    }

    private fun getOAuth2UserPrincipal(authentication: Authentication): OAuth2UserPrincipal? {
        val principal = authentication.principal

        return if (principal is OAuth2UserPrincipal) {
            principal
        } else {
            null
        }
    }

    protected fun clearAuthenticationAttributes(request: HttpServletRequest, response: HttpServletResponse) {
        super.clearAuthenticationAttributes(request)
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response)
    }

    companion object {
        private const val REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri"
        private fun generateUniqueCookieName(): String {
            return UUID.randomUUID().toString()
        }
    }
}
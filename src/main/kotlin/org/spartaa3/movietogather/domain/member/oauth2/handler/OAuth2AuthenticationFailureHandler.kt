package org.spartaa3.movietogather.domain.member.oauth2.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.spartaa3.movietogather.global.cookie.CookieUtils
import org.spartaa3.movietogather.global.cookie.HttpCookieOAuth2AuthorizationRequestRepository
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import java.io.IOException

@Component
class OAuth2AuthenticationFailureHandler(
    private val httpCookieOAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository
) : SimpleUrlAuthenticationFailureHandler() {

    @Throws(IOException::class)
    override fun onAuthenticationFailure(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        exception: AuthenticationException?
    ) {
        val targetUrl = request?.let {
            CookieUtils.getCookie(it, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map { it.value }
                .orElse("/")
        }

        val targetUri = targetUrl?.let {
            UriComponentsBuilder.fromUriString(it)
                .queryParam("error", exception?.localizedMessage)
                .build().toUriString()
        }

        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request!!, response!!)

        redirectStrategy.sendRedirect(request, response, targetUri)
    }

    companion object {
        private const val REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri"
    }
}
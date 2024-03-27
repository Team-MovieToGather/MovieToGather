package org.spartaa3.movietogather.domain.member.controller

import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletResponse
import org.spartaa3.movietogather.domain.member.dto.LoginResponse
import org.spartaa3.movietogather.domain.member.oauth2.OAuth2Provider
import org.spartaa3.movietogather.domain.member.service.OAuth2ClientService
import org.spartaa3.movietogather.domain.member.service.OAuth2LoginService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "oauth2", description = "소셜로그인")
@RestController
class OAuth2LoginController(
    private val oAuth2LoginService: OAuth2LoginService,
    private val oAuth2ClientService: OAuth2ClientService
) {

    @PreAuthorize("isAnonymous()")
    @GetMapping("/oauth2/login/{provider}")
    fun redirectLoginPage(@PathVariable provider: OAuth2Provider, response: HttpServletResponse) {
        val loginPageUrl = oAuth2ClientService.generateLoginPageUrl(provider)
        response.sendRedirect(loginPageUrl)
    }



    @PreAuthorize("isAnonymous()")
    @GetMapping("/oauth2/callback/{provider}")
    fun callback(
        @PathVariable provider: OAuth2Provider, response: HttpServletResponse,
        @RequestParam(name = "code") authorizationCode: String
    ): LoginResponse {
        return oAuth2LoginService.login(provider, response, authorizationCode)
    }
}
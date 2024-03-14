package org.spartaa3.movietogather.infra.security

import org.spartaa3.movietogather.domain.member.oauth2.handler.OAuth2AuthenticationFailureHandler
import org.spartaa3.movietogather.domain.member.oauth2.handler.OAuth2AuthenticationSuccessHandler
import org.spartaa3.movietogather.domain.member.service.CustomOAuth2MemberService
import org.spartaa3.movietogather.global.cookie.HttpCookieOAuth2AuthorizationRequestRepository
import org.spartaa3.movietogather.infra.security.jwt.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val customOAuth2MemberService: CustomOAuth2MemberService,
    private val oAuth2AuthenticationSuccessHandler: OAuth2AuthenticationSuccessHandler,
    private val oAuth2AuthenticationFailureHandler: OAuth2AuthenticationFailureHandler,
    private val httpCookieOAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository,
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http

            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .csrf { it.disable() }

            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.NEVER)
            }
            .oauth2Login {  oauth2Login -> oauth2Login
//                it.userInfoEndpoint { u -> u.userService(memberService) }
//                it.successHandler(oAuthLoginSuccessHandler)
//                it.defaultSuccessUrl("/home")
//                it.failureUrl("/fail")
                .userInfoEndpoint { it.userService(customOAuth2MemberService) }
                .authorizationEndpoint { it.authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository) }
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler)
//                .defaultSuccessUrl("/home")
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }
}
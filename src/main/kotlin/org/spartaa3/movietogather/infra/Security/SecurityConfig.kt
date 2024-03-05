package org.spartaa3.movietogather.infra.Security

import org.spartaa3.movietogather.domain.member.service.CustomMemberDetailService
import org.spartaa3.movietogather.infra.Security.jwt.JwtAuthenticationFilter
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
    private val customMemberDetailService: CustomMemberDetailService,
) {
//    private val allowedUrls = arrayOf(
//        "/", "/swagger-ui/**", "/v3/**",
//        "/api/**"
//    )

//    private val anonymousUrls = arrayOf(
//        "/members/socialLogin"
//    )
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf {
                it.disable()
            }
            .httpBasic {
                it.disable()
            }
            .authorizeHttpRequests {
                it
//                    .requestMatchers(*allowedUrls).permitAll()
//                    .requestMatchers(*anonymousUrls).anonymous()
                    .anyRequest()
                    .permitAll()
//                    .authenticated()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.NEVER)
            }
            .logout { auth ->
                auth
                    .logoutUrl("/auth/logout")
                    .logoutSuccessHandler { request, response, authentication ->
                        response.status = 200
                    }
            }
            .oauth2Login {
                it.userInfoEndpoint { u -> u.userService(customMemberDetailService) }
                it.defaultSuccessUrl("/auth/login")
                it.failureUrl("/fail")
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()

    }
}
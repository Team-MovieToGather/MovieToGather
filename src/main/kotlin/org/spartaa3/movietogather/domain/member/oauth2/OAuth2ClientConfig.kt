package org.spartaa3.movietogather.domain.member.oauth2

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository
import org.springframework.security.oauth2.core.AuthorizationGrantType

@Configuration
class OAuth2ClientConfig {

    @Bean
    fun clientRegistrationRepository(): InMemoryClientRegistrationRepository {
        val kakao: ClientRegistration = ClientRegistration.withRegistrationId("kakao")
            .clientId("\${AUTH_KAKAO_ID}")
            .clientSecret("\${AUTH_KAKAO_SECRET}")
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .authorizationUri("https://kauth.kakao.com/oauth/authorize")
            .tokenUri("https://kauth.kakao.com/oauth/token")
            .userInfoUri("https://kapi.kakao.com/v2/user/me")
            .redirectUri("http://movie2gather.net/oauth-redirect-kakao")
            .clientName("Kakao")
            .build()

        return InMemoryClientRegistrationRepository(kakao)
    }
}
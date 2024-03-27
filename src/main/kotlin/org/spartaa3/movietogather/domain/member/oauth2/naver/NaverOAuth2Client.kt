package org.spartaa3.movietogather.domain.member.oauth2.naver

import org.spartaa3.movietogather.domain.member.oauth2.OAuth2Client
import org.spartaa3.movietogather.domain.member.oauth2.OAuth2MemberInfo
import org.spartaa3.movietogather.domain.member.oauth2.OAuth2Provider
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Component
class NaverOAuth2Client(
    @Value("\${AUTH_NAVER_ID}") val clientId: String,
    @Value("\${AUTH_NAVER_SECRET}") val clientSecret: String,
    @Value("\${oauth2.naver.redirect_uri}") val redirectUrl: String,
    private val restClient: RestClient
) : OAuth2Client {

    override fun generateLoginPageUrl(): String {
        return StringBuilder(NAVER_AUTH_BASE_URL)
            .append("/authorize")
            .append("?response_type=").append("code")
            .append("&client_id=").append(clientId)
            .append("&redirect_uri=").append(redirectUrl)
            .toString()
    }

    override fun getAccessToken(authorizationCode: String): String {
        val requestData = mutableMapOf(
            "grant_type" to "authorization_code",
            "client_id" to clientId,
            "client_secret" to clientSecret,
            "code" to authorizationCode
        )
        return restClient.post()
            .uri("$NAVER_AUTH_BASE_URL/token")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(LinkedMultiValueMap<String, String>().apply { this.setAll(requestData) })
            .retrieve()
            .body<NaverTokenResponse>()
            ?.accessToken
            ?: throw RuntimeException("네이버 AccessToken 조회 실패")
    }

    override fun retrieveUserInfo(accessToken: String): OAuth2MemberInfo {
        return restClient.post()
            .uri("$NAVER_API_BASE_URL/nid/me")
            .header("Authorization", "Bearer $accessToken")
            .retrieve()
            .body<NaverResponse<NaverMemberResponse>>()
            ?.response
            ?: throw RuntimeException("네이버 UserInfo 조회 실패")
    }

    override fun supports(provider: OAuth2Provider): Boolean {
        return provider == OAuth2Provider.NAVER
    }

    companion object {
        private const val NAVER_AUTH_BASE_URL = "https://nid.naver.com/oauth2.0"
        private const val NAVER_API_BASE_URL = "https://openapi.naver.com/v1"
    }
}
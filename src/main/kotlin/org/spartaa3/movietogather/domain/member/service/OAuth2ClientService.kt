package org.spartaa3.movietogather.domain.member.service

import org.spartaa3.movietogather.domain.member.oauth2.OAuth2Client
import org.spartaa3.movietogather.domain.member.oauth2.OAuth2MemberInfo
import org.spartaa3.movietogather.domain.member.oauth2.OAuth2Provider
import org.springframework.stereotype.Component

@Component
class OAuth2ClientService(
    private val client: List<OAuth2Client>
) {
    fun login(provider: OAuth2Provider, authorizationCode: String): OAuth2MemberInfo {
        val client: OAuth2Client = this.selectClient(provider)
        return client.getAccessToken(authorizationCode)
            .let { client.retrieveUserInfo(it) }
    }

    fun generateLoginPageUrl(provider: OAuth2Provider): String {
        val client = this.selectClient(provider)
        return client.generateLoginPageUrl()
    }

    private fun selectClient(provider: OAuth2Provider): OAuth2Client {
        return client.find { it.supports(provider) }
            ?: throw RuntimeException("지원하지 않는 OAuth Provider 입니다.")
    }
}
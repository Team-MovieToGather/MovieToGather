package org.spartaa3.movietogather.domain.member.oauth2

interface OAuth2Client {
    fun generateLoginPageUrl(): String
    fun getAccessToken(authorizationCode: String): String
    fun retrieveUserInfo(accessToken: String): OAuth2MemberInfo
    fun supports(provider: OAuth2Provider): Boolean
}
package org.spartaa3.movietogather.domain.member.oauth2

open class OAuth2MemberInfo(
    val provider: OAuth2Provider,
    val id: String,
    val nickname: String,
    val email: String,
)
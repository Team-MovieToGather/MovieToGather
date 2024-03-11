package org.spartaa3.movietogather.domain.member.oauth2

enum class OAuth2Provider(val registrationId: String) {
    GOOGLE("google"),
    NAVER("naver"),
    KAKAO("kakao")
}

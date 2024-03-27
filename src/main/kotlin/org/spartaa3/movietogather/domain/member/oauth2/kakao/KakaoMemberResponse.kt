package org.spartaa3.movietogather.domain.member.oauth2.kakao

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.spartaa3.movietogather.domain.member.oauth2.OAuth2MemberInfo
import org.spartaa3.movietogather.domain.member.oauth2.OAuth2Provider

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
class KakaoMemberResponse(
    id: Long,
    properties: KakaoPropertiesResponse,
    kakaoAccount: KakaoAccountResponse,
) : OAuth2MemberInfo(
    provider = OAuth2Provider.KAKAO,
    id = id.toString(),
    nickname = properties.nickname,
    email = kakaoAccount.email
)
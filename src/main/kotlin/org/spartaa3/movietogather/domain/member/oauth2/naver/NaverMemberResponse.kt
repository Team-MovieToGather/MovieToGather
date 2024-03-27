package org.spartaa3.movietogather.domain.member.oauth2.naver

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.spartaa3.movietogather.domain.member.oauth2.OAuth2MemberInfo
import org.spartaa3.movietogather.domain.member.oauth2.OAuth2Provider

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
class NaverMemberResponse(
    id: String,
    nickname: String,
    email: String,
) : OAuth2MemberInfo(
    provider = OAuth2Provider.NAVER,
    id = id,
    nickname = nickname,
    email = email
)
package org.spartaa3.movietogather.domain.member.oauth2.google

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.spartaa3.movietogather.domain.member.oauth2.OAuth2MemberInfo
import org.spartaa3.movietogather.domain.member.oauth2.OAuth2Provider

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
class GoogleMemberResponse(
    id: String,
    name: String,
    email: String
) : OAuth2MemberInfo(
    provider = OAuth2Provider.GOOGLE,
    id = id,
    nickname = name,
    email = email
)
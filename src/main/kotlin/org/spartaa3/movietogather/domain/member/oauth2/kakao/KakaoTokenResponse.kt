package org.spartaa3.movietogather.domain.member.oauth2.kakao

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class KakaoTokenResponse(
    val accessToken: String
)
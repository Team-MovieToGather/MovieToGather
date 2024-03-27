package org.spartaa3.movietogather.domain.member.oauth2.naver

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
class NaverResponse<T>(
    @JsonProperty("resultcode") val code: String,
    val message: String,
    val response: T
)
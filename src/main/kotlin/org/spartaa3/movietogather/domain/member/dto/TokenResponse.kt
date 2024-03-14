package org.spartaa3.movietogather.domain.member.dto

data class TokenResponse(
    val id: Long,
    val email: String,
    val member_id: Long?,
    val refreshToken: String
)
package org.spartaa3.movietogather.infra.security.jwt

data class JwtDto(
    val grantType: String,
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpires: Long
)
package org.spartaa3.movietogather.infra.security.refresh

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

@RedisHash(value = "refreshToken", timeToLive = 60)
class RefreshToken(
    @Id
    @Indexed
    val refreshToken: String,
    val memberId: Long,
)
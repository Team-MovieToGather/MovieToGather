package org.spartaa3.movietogather.infra.redis

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RedissonConfig(
    @Value("\${spring.data.redis.host}") val host: String,
    @Value("\${spring.data.redis.port}") val port: Int
) {

    @Bean
    fun redisson(): RedissonClient {
        val config = Config()
        config.useSingleServer().setAddress("redis://$host:$port")
        return Redisson.create(config)
    }
}
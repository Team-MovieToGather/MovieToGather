package org.spartaa3.movietogather.domain.review.repository


import com.fasterxml.jackson.module.kotlin.readValue
import org.spartaa3.movietogather.domain.review.entity.Review
import org.spartaa3.movietogather.global.objectmapper.MapperConfig
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class RedisRepository(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val objectMapper: MapperConfig
) {
    private val mapper = objectMapper.objectMapper()
    fun saveBestReviews(bestReviews: List<Review>){
        val reviewJson = mapper.writeValueAsString(bestReviews)
        redisTemplate.opsForValue().set("bestReviews", reviewJson, 60L, TimeUnit.SECONDS)
    }

    fun getBestReviews(): List<Review>? {
        val reviewJson = redisTemplate.opsForValue().get("bestReviews") as String?
        return reviewJson?.let {
            mapper.readValue<List<Review>>(it)
        }
    }
}
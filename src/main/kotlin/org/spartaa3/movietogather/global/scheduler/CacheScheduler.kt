package org.spartaa3.movietogather.global.scheduler

import org.springframework.cache.CacheManager
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class CacheScheduler(
    private val cacheManager: CacheManager,
) {
    @Scheduled(cron = "0 0 0 * * ?")
    fun evictCaches() {
        evictMovies("movies")
    }

    private fun evictMovies(cacheName: String) {
        cacheManager.getCache(cacheName)?.clear()
    }
}
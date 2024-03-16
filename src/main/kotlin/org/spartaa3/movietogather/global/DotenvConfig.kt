package org.spartaa3.movietogather.global

import io.github.cdimascio.dotenv.Dotenv
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DotenvConfig {
    @Bean
    fun requestInterceptor(): Dotenv? {
        return Dotenv.configure()
            .ignoreIfMalformed()
            .ignoreIfMissing()
            .load()
    }
}
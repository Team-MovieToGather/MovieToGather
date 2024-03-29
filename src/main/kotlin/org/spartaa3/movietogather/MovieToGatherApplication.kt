package org.spartaa3.movietogather

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@EnableCaching
@SpringBootApplication
class MovieToGatherApplication

fun main(args: Array<String>) {
    runApplication<MovieToGatherApplication>(*args)
}

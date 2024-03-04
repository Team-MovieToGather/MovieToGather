package org.spartaa3.movietogather.infra.api

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "api")
class ApiProperties {
    lateinit var key: String
    lateinit var popularUrl: String
    lateinit var genreUrl: String
}
package org.spartaa3.movietogather.global.config

import org.spartaa3.movietogather.infra.api.ApiProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(ApiProperties::class)
class PropertyConfig {
}
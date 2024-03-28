package org.spartaa3.movietogather.global.config

import org.spartaa3.movietogather.domain.member.oauth2.OAuth2ProviderConverter
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.http.CacheControl
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.util.concurrent.TimeUnit

@Configuration
class WebConfig : WebMvcConfigurer {
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/**")
            .addResourceLocations("classpath:/templates", "classpath:/static")
            .setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES))
    }
    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverter(OAuth2ProviderConverter())
    }
//    override fun addCorsMappings(registry: CorsRegistry) {
//        registry.addMapping("/**")
//            .allowedOrigins("http://localhost:3000")
//            .allowedMethods("*")
//            .allowCredentials(true)
//    }
}

package org.spartaa3.movietogather.infra.audit

import org.spartaa3.movietogather.infra.security.jwt.CustomUserPrincipal
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

@Configuration
class AuditConfig {
    @Bean
    fun auditorAware() = AuditorAware {
        Optional.ofNullable(SecurityContextHolder.getContext())
            .map { it.authentication }
            .filter { it.isAuthenticated && !it.name.equals("anonymousUser") }
            .map { it.principal as CustomUserPrincipal }
            .map { it.email }
    }
}
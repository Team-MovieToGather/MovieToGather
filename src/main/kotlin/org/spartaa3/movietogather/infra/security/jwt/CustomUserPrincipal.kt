package org.spartaa3.movietogather.infra.security.jwt

import org.springframework.security.core.GrantedAuthority

abstract class CustomUserPrincipal(
    open val email: String,
    open val provider: String,
    open val authorities: Collection<GrantedAuthority>
)
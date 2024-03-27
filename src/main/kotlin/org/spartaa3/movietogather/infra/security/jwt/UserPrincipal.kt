package org.spartaa3.movietogather.infra.security.jwt

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

data class UserPrincipal(
    override val email: String,
    override val provider: String,
    override val authorities: Collection<GrantedAuthority>
) : CustomUserPrincipal(email, provider, authorities){
    constructor(email: String, roles: Set<String>, provider: String) : this(
        email,
        provider,
        roles.map { SimpleGrantedAuthority("ROLE_$it") }
    )
}

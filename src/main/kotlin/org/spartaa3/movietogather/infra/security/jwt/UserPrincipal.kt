package org.spartaa3.movietogather.infra.security.jwt

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

data class UserPrincipal(
    override val email: String,
    override val oauthType: String,
    override val authorities: Collection<GrantedAuthority>
) : CustomUserPrincipal(email, oauthType, authorities) {
    constructor(email: String, roles: Set<String>, oauthType: String) : this(
        email,
        oauthType,
        roles.map { SimpleGrantedAuthority("ROLE_$it") }
    )
}

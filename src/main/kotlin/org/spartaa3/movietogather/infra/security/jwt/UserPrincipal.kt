package org.spartaa3.movietogather.infra.security.jwt

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

data class UserPrincipal(
    val id: Int,
    val email: String,
    val oauthType: String,
    val authorities: Collection<GrantedAuthority>
) {
    constructor(id: Int, email: String, roles: Set<String>, oauthType: String): this(
        id,
        email,
        oauthType,
        roles.map { SimpleGrantedAuthority("ROLE_$it") }
    )
}

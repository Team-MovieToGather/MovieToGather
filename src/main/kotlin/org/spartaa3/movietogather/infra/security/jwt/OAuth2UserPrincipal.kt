package org.spartaa3.movietogather.infra.security.jwt

import org.spartaa3.movietogather.domain.member.oauth2.info.MemberInfo
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User

class OAuth2UserPrincipal(val memberInfo: MemberInfo) : OAuth2User, UserDetails {

    override fun getPassword(): String? {
        return null
    }

    override fun getUsername(): String? {
        return memberInfo.getEmail()
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getAttributes(): Map<String, Any> {
        return memberInfo.getAttributes()
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return emptyList() // No authorities for now
    }

    override fun getName(): String? {
        return memberInfo.getEmail()
    }

    fun getInfo(): MemberInfo {
        return memberInfo
    }
}
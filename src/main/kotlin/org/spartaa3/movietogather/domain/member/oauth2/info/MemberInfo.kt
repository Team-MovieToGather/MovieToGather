package org.spartaa3.movietogather.domain.member.oauth2.info

import org.spartaa3.movietogather.domain.member.oauth2.OAuth2Provider

interface MemberInfo {
    fun getProvider(): OAuth2Provider
    fun getAccessToken(): String
    fun getAttributes(): Map<String, Any>
    fun getId(): String?
    fun getEmail(): String?
    fun getName(): String?
    fun getFirstName(): String?
    fun getLastName(): String?
    fun getNickname(): String?
    fun getProfileImageUrl(): String?
}

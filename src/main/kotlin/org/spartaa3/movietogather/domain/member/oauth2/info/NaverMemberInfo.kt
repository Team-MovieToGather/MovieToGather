package org.spartaa3.movietogather.domain.member.oauth2.info

import org.spartaa3.movietogather.domain.member.oauth2.OAuth2Provider

class NaverMemberInfo(
    private val accessToken: String,
    attributes: Map<String, Any>
) : MemberInfo {

    private val attributes: Map<String, Any> = attributes["response"] as Map<String, Any>
    private val id: String? = attributes["id"] as? String
    private val email: String? = (attributes["response"] as? Map<String, Any>)?.get("email") as? String
    private val name: String? = attributes["name"] as? String
    private val nickName: String? = attributes["nickname"] as? String
    private val profileImageUrl: String? = attributes["profile_image"] as? String

    override fun getProvider(): OAuth2Provider {
        return OAuth2Provider.NAVER
    }

    override fun getAccessToken(): String {
        return accessToken
    }

    override fun getAttributes(): Map<String, Any> {
        return attributes
    }

    override fun getId(): String? {
        return id
    }

    override fun getEmail(): String? {
        return email
    }

    override fun getName(): String? {
        return name
    }

    override fun getFirstName(): String? {
        return null
    }

    override fun getLastName(): String? {
        return null
    }

    override fun getNickname(): String? {
        return nickName
    }

    override fun getProfileImageUrl(): String? {
        return profileImageUrl
    }
}

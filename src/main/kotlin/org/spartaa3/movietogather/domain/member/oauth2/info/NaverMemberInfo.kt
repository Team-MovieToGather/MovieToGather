package org.spartaa3.movietogather.domain.member.oauth2.info

import org.spartaa3.movietogather.domain.member.oauth2.OAuth2Provider


class NaverMemberInfo(
    private val accessToken: String,
    private val attributes: Map<String, Any>
) : MemberInfo {

    private val response: Map<String, Any> = attributes["response"] as Map<String, Any>
    private val oAuthId = response["id"] as? String
    private val email = response["email"] as? String
    private val nickName = response["nickname"] as? String

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
        return oAuthId
    }

    override fun getEmail(): String? {
        return email
    }

//    override fun getName(): String? {
//        return name
//    }
//
//    override fun getFirstName(): String? {
//        return null
//    }
//
//    override fun getLastName(): String? {
//        return null
//    }

    override fun getNickname(): String? {
        return nickName
    }
//
//    override fun getProfileImageUrl(): String? {
//        return profileImageUrl
//    }
}

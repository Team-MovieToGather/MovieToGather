package org.spartaa3.movietogather.domain.trash.member.oauth2.info

import org.spartaa3.movietogather.domain.member.oauth2.OAuth2Provider

class KakaoMemberInfo(
    private val accessToken: String,
    private val attributes: Map<String, Any> // attributes 필드 추가
) : MemberInfo {

    private val oAuthId: String = (attributes["id"] as Long).toString()
    private val email: String? = (attributes["kakao_account"] as? Map<*, *>)?.get("email") as? String
    private val nickName: String? = attributes["nickname"] as? String
    private val profileImageUrl: String? = attributes["profile_image_url"] as? String

    override fun getProvider(): OAuth2Provider {
        return OAuth2Provider.KAKAO
    }

    override fun getAccessToken(): String {
        return accessToken
    }

    override fun getAttributes(): Map<String, Any> {
        return mutableMapOf<String, Any>().apply {
            putAll(attributes)
            put("id", oAuthId)
            put("email", email ?: "")
        }
    }

    override fun getId(): String {
        return oAuthId
    }

    override fun getEmail(): String? {
        return email
    }

//    override fun getName(): String? {
//        return null
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

//    override fun getProfileImageUrl(): String? {
//        return profileImageUrl
//    }
}

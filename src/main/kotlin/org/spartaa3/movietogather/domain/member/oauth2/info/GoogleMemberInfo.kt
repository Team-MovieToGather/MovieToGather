package org.spartaa3.movietogather.domain.member.oauth2.info

import org.spartaa3.movietogather.domain.member.oauth2.OAuth2Provider

class GoogleMemberInfo(
    private val accessToken: String,
    private val attributes: Map<String, Any>
) : MemberInfo {

    private val oAuthId: String? = attributes["sub"] as String?
    private val email: String? = attributes["email"] as String?
    private val name: String? = attributes["name"] as String?
    private val firstName: String? = attributes["given_name"] as String?
    private val lastName: String? = attributes["family_name"] as String?
    private val profileImageUrl: String? = attributes["picture"] as String?

    override fun getProvider(): OAuth2Provider {
        return OAuth2Provider.GOOGLE
    }

    override fun getAccessToken(): String {
        return accessToken
    }

    override fun getAttributes(): Map<String, Any> {
        return attributes
    }

    override fun getId(): String {
        return oAuthId!!
    }

    override fun getEmail(): String? {
        return email
    }

//    override fun getName(): String? {
//        return name
//    }

//    override fun getFirstName(): String? {
//        return firstName
//    }
//
//    override fun getLastName(): String? {
//        return lastName
//    }

    override fun getNickname(): String? {
        return null
    }

//    override fun getProfileImageUrl(): String? {
//        return profileImageUrl
//    }
}

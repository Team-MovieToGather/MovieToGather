package org.spartaa3.movietogather.domain.member.dto

data class OAuthAttributes(
    val attributes: Map<String, Any>,
    val nameAttributeKey: String,
    val nickname: String,
    val email: String
) {
    companion object {
        fun of(
            registrationId: String,
            memberNameAttributeName: String,
            attributes: Map<String, Any>
        ): OAuthAttributes {
            return OAuthAttributes(
                nickname = attributes["nickname"] as String,
                // nickname = attributes["properties"] as String 이건 카카오!
                // nickname = attributes["response"] as String 이건 네이버!
                email = attributes["email"] as String,
                // nickname = attributes["properties"] as String 이건 카카오!
                // nickname = attributes["response"] as String 이건 네이버!
                attributes = attributes,
                nameAttributeKey = memberNameAttributeName
            )
        }
    }
}
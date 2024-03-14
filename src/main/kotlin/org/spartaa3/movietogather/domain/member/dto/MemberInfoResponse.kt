package org.spartaa3.movietogather.domain.member.dto

import org.spartaa3.movietogather.domain.member.entity.Member

data class MemberInfoResponse(
    val email: String,
    val nickname: String,
    val oauthType: String
) {
    companion object {
        fun info(member: Member): MemberInfoResponse {
            return MemberInfoResponse(
                email = member.email,
                nickname = member.nickname,
                oauthType = member.OAuthType.toString()
            )
        }
    }
}
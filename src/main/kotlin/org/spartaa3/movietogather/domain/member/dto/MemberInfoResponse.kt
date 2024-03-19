package org.spartaa3.movietogather.domain.member.dto

import org.spartaa3.movietogather.domain.member.entity.Member

data class MemberInfoResponse(
    val email: String,
    val nickname: String,
    val oAuthType: String
) {
    companion object {
        fun info(member: Member): MemberInfoResponse {
            return MemberInfoResponse(
                email = member.email,
                nickname = member.nickname,
                oAuthType = member.OAuthType.toString()
            )
        }
    }
}
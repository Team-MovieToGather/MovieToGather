package org.spartaa3.movietogather.domain.member.dto

import org.spartaa3.movietogather.domain.member.entity.Member
import org.spartaa3.movietogather.domain.member.oauth2.OAuth2Provider

data class MemberInfoResponse(
    val email: String,
    val nickname: String,
    val provider: OAuth2Provider
) {
    companion object {
        fun info(member: Member): MemberInfoResponse {
            return MemberInfoResponse(
                email = member.email,
                nickname = member.nickname,
                provider = OAuth2Provider.valueOf(toString())
            )
        }
    }
}
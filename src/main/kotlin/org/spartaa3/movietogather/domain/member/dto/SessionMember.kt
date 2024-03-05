package org.spartaa3.movietogather.domain.member.dto

import org.spartaa3.movietogather.domain.member.entity.Member
import java.io.Serializable


data class SessionMember(
    private val member: Member
): Serializable {
    val nickname = member.nickname
    val email = member.email
}
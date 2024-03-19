package org.spartaa3.movietogather.domain.member.service

import org.spartaa3.movietogather.domain.member.dto.MemberInfoResponse
import org.spartaa3.movietogather.domain.member.dto.UpdateMemberInfoRequest
import org.spartaa3.movietogather.infra.security.jwt.UserPrincipal

interface MemberService {
    fun getMemberInfo(userPrincipal: UserPrincipal): MemberInfoResponse
    fun updateMemberInfo(userPrincipal: UserPrincipal, request: UpdateMemberInfoRequest): MemberInfoResponse
}
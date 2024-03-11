package org.spartaa3.movietogather.domain.member.service

import org.spartaa3.movietogather.domain.member.dto.MemberInfoResponse
import org.spartaa3.movietogather.domain.member.dto.UpdateMemberInfoRequest

interface MemberService {
    fun getMemberInfo(id: Long): MemberInfoResponse
    fun updateMemberInfo(id: Long, request: UpdateMemberInfoRequest): MemberInfoResponse
}
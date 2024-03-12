package org.spartaa3.movietogather.domain.member.service

import jakarta.servlet.http.HttpSession
import org.spartaa3.movietogather.domain.member.dto.MemberInfoResponse
import org.spartaa3.movietogather.domain.member.dto.UpdateMemberInfoRequest
import org.spartaa3.movietogather.domain.member.entity.MemberToken
import org.spartaa3.movietogather.domain.member.repository.MemberRepository
import org.spartaa3.movietogather.domain.member.repository.TokenRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberServiceImpl(
    private val memberRepository: MemberRepository,
): MemberService {

    override fun getMemberInfo(id: Long): MemberInfoResponse {
        val member = memberRepository.findByIdOrNull(id)?: throw IllegalStateException("$id not found")
        return member.let { MemberInfoResponse.info(it) }
    }
    @Transactional
    override fun updateMemberInfo(id: Long, request: UpdateMemberInfoRequest): MemberInfoResponse {
        val member = memberRepository.findByIdOrNull(id)?: throw IllegalStateException("$id not found")
        member.nickname = request.nickname
        return member.let { MemberInfoResponse.info(it) }
    }

}
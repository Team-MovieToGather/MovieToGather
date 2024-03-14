package org.spartaa3.movietogather.domain.member.service


import org.spartaa3.movietogather.domain.member.dto.MemberInfoResponse
import org.spartaa3.movietogather.domain.member.dto.UpdateMemberInfoRequest
import org.spartaa3.movietogather.domain.member.repository.MemberRepository
import org.spartaa3.movietogather.infra.security.jwt.UserPrincipal
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberServiceImpl(
    private val memberRepository: MemberRepository,
): MemberService {

    override fun getMemberInfo(userPrincipal: UserPrincipal): MemberInfoResponse {
        val member = memberRepository.findByEmail(userPrincipal.email)
        return member.let { MemberInfoResponse.info(it) }
    }
    @Transactional
    override fun updateMemberInfo(userPrincipal: UserPrincipal, request: UpdateMemberInfoRequest): MemberInfoResponse {
        val member = memberRepository.findByEmail(userPrincipal.email)
        member.nickname = request.nickname
        return member.let { MemberInfoResponse.info(it) }
    }

}
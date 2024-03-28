package org.spartaa3.movietogather.domain.member.service

import org.spartaa3.movietogather.domain.member.entity.Member
import org.spartaa3.movietogather.domain.member.entity.MemberRole
import org.spartaa3.movietogather.domain.member.oauth2.OAuth2MemberInfo
import org.spartaa3.movietogather.domain.member.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class OAuth2MemberService(
    private val memberRepository: MemberRepository
) {
    fun registerIfAbsent(memberInfo: OAuth2MemberInfo): Member {
        return if (!memberRepository.existsByEmailAndProvider(memberInfo.email,memberInfo.provider)) {
            val member = Member(
                role = MemberRole.MEMBER,
                nickname = memberInfo.nickname,
                email = memberInfo.email,
                provider = memberInfo.provider,
                providerId = memberInfo.id,
            )
            memberRepository.save(member)
        } else {
            memberRepository.findByEmailAndProvider(memberInfo.email, memberInfo.provider)
        }
    }
}
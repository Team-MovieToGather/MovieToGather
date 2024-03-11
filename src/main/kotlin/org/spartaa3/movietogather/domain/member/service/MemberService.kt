package org.spartaa3.movietogather.domain.member.service

import org.spartaa3.movietogather.domain.member.dto.LoginResponse
import org.spartaa3.movietogather.domain.member.entity.Member
import org.spartaa3.movietogather.domain.member.repository.MemberRepository
import org.spartaa3.movietogather.infra.security.jwt.JwtPlugin
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val jwtPlugin: JwtPlugin
) {

    @Transactional
    fun socialLogin(oAuth2User: OAuth2User): LoginResponse {
        if (!memberRepository.existsByEmail(oAuth2User.attributes["email"] as String)) {
            val member = Member(
                email = oAuth2User.attributes["email"] as String,
                nickname = "nickname",
                role = Member.MemberRole.MEMBER
            )
            memberRepository.save(member)
        }
        val member = memberRepository.findByEmail(oAuth2User.attributes["email"] as String)
        return LoginResponse(
            accessToken = jwtPlugin.generateToken(
                memberId = member!!.id!!,
                email = member.email,
                role = member.role.name

            )
        )
    }


}


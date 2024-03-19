package org.spartaa3.movietogather.domain.member.service

import org.spartaa3.movietogather.domain.member.entity.MemberToken
import org.spartaa3.movietogather.domain.member.repository.MemberRepository
import org.spartaa3.movietogather.domain.member.repository.TokenRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TokenServiceImpl(
    private val tokenRepository: TokenRepository,
    private val memberRepository: MemberRepository
){

    @Transactional
    fun saveRefreshToken(email: String, refreshTokenValue: String) {
        val member = memberRepository.findByEmail(email)
        val refreshToken = MemberToken(
            member = member,
//            email = email,
            refreshToken = refreshTokenValue
        )
        tokenRepository.save(refreshToken)
    }
}
package org.spartaa3.movietogather.domain.member.service

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.*
import org.spartaa3.movietogather.domain.member.entity.Member
import org.spartaa3.movietogather.domain.member.repository.MemberRepository
import org.spartaa3.movietogather.infra.security.jwt.JwtPlugin
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
class MemberServiceTest : BehaviorSpec({

    afterContainer {
        clearAllMocks()
    }

    val memberRepository = mockk<MemberRepository>()
    val jwtPlugin = mockk<JwtPlugin>(relaxed = true)
    val memberService = MemberService(memberRepository, jwtPlugin)
    val oAuth2User = spyk<OAuth2User>()

    // 테스트가 어려움 -> 로직 변경?
    given("소셜 로그인을 시도할 때") {
        `when`("회원가입이 되어 있지 않다면") {
            every { memberRepository.existsByEmail(any()) } returns false
            then("회원 가입이 진행되어야 한다.") {
                val jwtToken = jwtPlugin.generateToken(
                    memberId = 1L,
                    email = "jeay@gmail.com",
                    role = "MEMBER"
                )
                every { jwtPlugin.generateToken(1L, "jeay@gmail.com", "MEMBER") } returns jwtToken
            }


        }
        `when`("회원가입이 되어 있다면") {
            every { memberRepository.existsByEmail(any()) } returns true
            then("로그인을 진행해야 한다.") {
            }
        }
    }
})

internal class MovieBehaviorSpec : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf
})


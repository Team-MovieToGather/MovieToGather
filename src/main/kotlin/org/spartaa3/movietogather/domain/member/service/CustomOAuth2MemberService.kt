package org.spartaa3.movietogather.domain.member.service

import mu.KotlinLogging
import org.spartaa3.movietogather.global.exception.OAuth2AuthenticationProcessingException
import org.spartaa3.movietogather.domain.member.entity.Member
import org.spartaa3.movietogather.domain.member.entity.MemberRole
import org.spartaa3.movietogather.domain.member.oauth2.OAuth2MemberInfoFactory
import org.spartaa3.movietogather.domain.member.repository.MemberRepository
import org.spartaa3.movietogather.infra.security.jwt.OAuth2UserPrincipal
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import java.util.*
import kotlin.random.Random

@Service
class CustomOAuth2MemberService(
    private val memberRepository: MemberRepository
) : DefaultOAuth2UserService() {

    private val log = KotlinLogging.logger {}

    @Throws(OAuth2AuthenticationException::class)
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val attributes = super.loadUser(userRequest).attributes
        val oAuth2User = super.loadUser(userRequest)
        log.info("ATTR INFO : {}", attributes.toString())

        var email: String? = null
        val oauthType = userRequest.clientRegistration.registrationId.lowercase(Locale.getDefault())

        email = when (oauthType) {
            "kakao" -> (attributes["kakao_account"] as Map<*, *>)["email"].toString()
            "google" -> attributes["email"].toString()
            "naver" -> (attributes["response"] as Map<*, *>)["email"].toString()
            else -> null
        }

        // Member 존재여부 확인 및 없으면 생성
        if (getMemberByEmailAndOAuthType(email, oauthType) == null) {
            log.info("{}({}) NOT EXISTS. REGISTER", email, oauthType)
            val random = Random.nextInt(100000)
            val member = Member(
                email = email.toString(),
                role = MemberRole.MEMBER,
                nickname = "nickname${random}",//중복 없이 랜덤부여 할 수 있도록 수정 필요
                OAuthType = oauthType
            )
            save(member)
        }

        return try {
            processOAuth2User(userRequest, oAuth2User)
        } catch (ex: AuthenticationException) {
            throw ex
        } catch (ex: Exception) {
            throw InternalAuthenticationServiceException(ex.message, ex)
        }
    }

    private fun processOAuth2User(userRequest: OAuth2UserRequest, oAuth2User: OAuth2User): OAuth2User {
        val registrationId = userRequest.clientRegistration.registrationId
        val accessToken = userRequest.accessToken.tokenValue

        val memberInfo = OAuth2MemberInfoFactory.getMemberInfo(
            registrationId,
            accessToken,
            oAuth2User.attributes
        )

        if (!StringUtils.hasText(memberInfo.getEmail())) {
            throw OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider")
        }

        return OAuth2UserPrincipal(memberInfo)
    }

    fun save(member: Member) {
        memberRepository.save(member)
    }

    fun getMemberByEmailAndOAuthType(email: String?, oauthType: String): Member? {
        return memberRepository.findByEmailAndOAuthType(email.toString(), oauthType)
    }

}
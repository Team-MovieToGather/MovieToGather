package org.spartaa3.movietogather.domain.member.service//package org.spartaa3.movietogather.domain.member.service
//
//import io.lettuce.core.ShutdownArgs.Builder.save
//import org.spartaa3.movietogather.domain.trash.member.entity.Member
//import org.spartaa3.movietogather.domain.trash.member.entity.MemberRole
//import org.spartaa3.movietogather.domain.trash.member.oauth2.OAuth2Provider
//import org.spartaa3.movietogather.domain.trash.member.repository.MemberRepository
//import org.spartaa3.movietogather.infra.security.jwt.JwtDto
//import org.spartaa3.movietogather.infra.security.jwt.JwtPlugin
//import org.springframework.security.oauth2.core.user.OAuth2User
//import org.springframework.stereotype.Service
//import java.util.*
//import kotlin.random.Random
//
//@Service
//class OAtuh2LoginService(
//    private val memberRepository: MemberRepository,
//    private val jwtPlugin: JwtPlugin
//
//) {
//    fun googleLogin(oAuth2User: OAuth2User): JwtDto {
//        val oAuthType = OAuth2Provider.GOOGLE
//        val email = oAuth2User.attributes.get("email").toString()
//        if (findByEmailAndOAuthType(email, oAuthType) == null) {
//            log.info("{}({}) NOT EXISTS. REGISTER", email, oAuthType)
//            val random = Random.nextInt(100000)
//            val member = Member(
//                email = email.toString(),
//                role = MemberRole.MEMBER,
//                nickname = "nickname${random}",//중복 없이 랜덤부여 할 수 있도록 수정 필요
//                oauthType = oAuthType.toString()
//            )
//            save(member)
//        }
//    }
//}
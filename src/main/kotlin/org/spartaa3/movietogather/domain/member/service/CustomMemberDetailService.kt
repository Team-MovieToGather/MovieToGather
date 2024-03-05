package org.spartaa3.movietogather.domain.member.service

import jakarta.servlet.http.HttpSession
import org.spartaa3.movietogather.domain.member.dto.OAuthAttributes
import org.spartaa3.movietogather.domain.member.dto.SessionMember
import org.spartaa3.movietogather.domain.member.entity.Member
import org.spartaa3.movietogather.domain.member.entity.toEntity
import org.spartaa3.movietogather.domain.member.repository.MemberRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

//@Service
//class CustomMemberDetailService: DefaultOAuth2UserService() {
//
//    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
//        val loadUser = super.loadUser(userRequest)
//        return loadUser
//    }
//}

@Service
class CustomMemberDetailService(
    private val memberRepository: MemberRepository,
    private val httpSession: HttpSession
): OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        if (userRequest == null) throw OAuth2AuthenticationException("Error")

        val delegate = DefaultOAuth2UserService()
        val oAuth2User = delegate.loadUser(userRequest)

        // registrationId는 로그인 진행중인 서비스 코드
        // 구글, 네이버, 카카오등을 구분하는 것이기에 현재는 사실 필요없음
        val registrationId = userRequest.clientRegistration.registrationId
        // OAuth2 로그인 진행시 키가 되는 필드값
        val userNameAttributeName = userRequest.clientRegistration.providerDetails.userInfoEndpoint.userNameAttributeName

        // OAuth2User의 attribute가 된다.
        // 추후 다른 소셜 로그인도 이 클래스를 쓰게 될 것이다.
        val attributes = OAuthAttributes.of(
            registrationId,
            userNameAttributeName,
            oAuth2User.attributes
        )
        // 전달받은 OAuth2User의 attribute를 이용하여 회원가입 및 수정의 역할을 한다.
        // User Entity 생성 : 회원가입
        // User Entity 수정 : update
        val member = saveOrUpdate(attributes)

        // session에 SessionUser(user의 정보를 담는 객체)를 담아 저장한다.
        httpSession.setAttribute("user", SessionMember(member))

        return DefaultOAuth2User(
            setOf(SimpleGrantedAuthority(member.role.name)),
            attributes.attributes,
            attributes.nameAttributeKey
        )
    }

//    fun saveOrUpdate(attributes: OAuthAttributes): Member {
//        val member = memberRepository.findByEmail(attributes.email)
//            ?. let { entity -> entity.update(attributes.nickname) }(nickname = attributes.nickname)
//            ?: attributes.toEntity()
//
//        return memberRepository.save(member)
//    }
fun saveOrUpdate(attributes: OAuthAttributes): Member {
    var member = memberRepository.findByEmail(attributes.email)
    if (member != null) {
        member.nickname = attributes.nickname
    } else {
        member = attributes.toEntity()
    }

    return memberRepository.save(member)
}
}
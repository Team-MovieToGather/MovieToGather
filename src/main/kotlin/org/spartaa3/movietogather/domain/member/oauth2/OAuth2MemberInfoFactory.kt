package org.spartaa3.movietogather.domain.member.oauth2

import org.spartaa3.movietogather.domain.member.oauth2.info.GoogleMemberInfo
import org.spartaa3.movietogather.domain.member.oauth2.info.KakaoMemberInfo
import org.spartaa3.movietogather.domain.member.oauth2.info.MemberInfo
import org.spartaa3.movietogather.domain.member.oauth2.info.NaverMemberInfo
import org.springframework.stereotype.Component
import java.util.*

@Component
object OAuth2MemberInfoFactory {

    fun getMemberInfo(
        registrationId: String,
        accessToken: String,
        attributes: Map<String, Any>
    ): MemberInfo {
        return when (OAuth2Provider.valueOf(registrationId.uppercase(Locale.getDefault()))) {
            OAuth2Provider.GOOGLE -> GoogleMemberInfo(accessToken, attributes)
            OAuth2Provider.NAVER -> NaverMemberInfo(accessToken, attributes)
            OAuth2Provider.KAKAO -> KakaoMemberInfo(accessToken, attributes)
        }
    }
}

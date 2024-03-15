package org.spartaa3.movietogather.domain.member.entity

import jakarta.persistence.*
import org.spartaa3.movietogather.infra.audit.BaseTimeEntity

@Entity
@Table(name = "member")
class Member(

    @Enumerated(EnumType.STRING)
    var role: MemberRole,

    @Column(name = "oauthtype", columnDefinition = "VARCHAR(50)")
    var OAuthType: String? = null,

    @Column(unique = true)
    var email: String,
    var nickname: String
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    //Enum Class가 소문자가 섞여있어 대문자로 변경
    enum class MemberRole {
        MEMBER, ADMIN
    }
}
package org.spartaa3.movietogather.domain.member.entity

import jakarta.persistence.*
import org.spartaa3.movietogather.domain.member.oauth2.OAuth2Provider
import org.spartaa3.movietogather.infra.audit.BaseTimeEntity

@Entity
@Table(name = "member")
class Member(

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    var role: MemberRole = MemberRole.MEMBER,

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", columnDefinition = "VARCHAR(50)")
    val provider: OAuth2Provider? = null,

    @Column(name = "provider_id", nullable = false)
    val providerId: String,

    @Column(name = "email",unique = true)
    var email: String,

    @Column(name = "nickname", unique = true)
    var nickname: String
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null


}
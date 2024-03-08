package org.spartaa3.movietogather.domain.member.entity

import jakarta.persistence.*

@Entity
@Table(name = "member")
class Member(

    @Enumerated(EnumType.STRING)
    var role: MemberRole,

    @Column(unique = true)
    var email: String,
    var nickname: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    enum class MemberRole {
        Member, Admin
    }
}
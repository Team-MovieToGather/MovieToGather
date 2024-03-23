package org.spartaa3.movietogather.domain.member.entity

import jakarta.persistence.*
import lombok.AccessLevel
import lombok.NoArgsConstructor

@Entity
@Table(name = "member_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class MemberToken(

    @Column(name = "email")
    var email: String,

    @Column(name = "refresh_token", columnDefinition = "TEXT", nullable = false)
    var refreshToken: String? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member? = null
) {
    @Id
    @Column(name = "member_token_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

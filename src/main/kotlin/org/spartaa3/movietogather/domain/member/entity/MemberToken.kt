package org.spartaa3.movietogather.domain.member.entity

import jakarta.persistence.*
import lombok.AccessLevel
import lombok.NoArgsConstructor

@Entity
@Table(name = "member_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
data class MemberToken(

    @ManyToOne
    @JoinColumn(name = "member_id")
    val member: Member,

    @Column(name = "email", nullable = false)
    var email: String,

    @Column(name = "refresh_token", columnDefinition = "TEXT", nullable = true)
    var refreshToken: String? = null,

) {
    @Id
    @Column(name = "member_token_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

package org.spartaa3.movietogather.domain.member.entity

import jakarta.persistence.*
import lombok.AccessLevel
import lombok.Builder
import lombok.NoArgsConstructor

@Entity
@Table(name = "member_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class MemberToken(

    @Id
    @Column(name = "member_token_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "email")
    var email: String? = null,

    @Column(name = "refresh_token", nullable = false)
    var refreshToken: String? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member? = null

) {
    @Builder
    constructor(refreshToken: String?, member: Member?) : this() {
        this.refreshToken = refreshToken
        this.member = member
    }
//    companion object {
//        @JvmStatic
//        fun builder(): MemberTokenBuilder {
//            return MemberTokenBuilder()
//        }
//    }
}

//class MemberTokenBuilder {
//    private var accessToken: String? = null
//    private var refreshToken: String? = null
//    private var member: Member? = null
//    private var email: email? = null
//
//    fun accessToken(accessToken: String?) = apply { this.accessToken = accessToken }
//    fun refreshToken(refreshToken: String?) = apply { this.refreshToken = refreshToken }
//    fun user(member: Member?) = apply { this.member = member }
//
//    fun build(): MemberToken {
//        return MemberToken(accessToken = accessToken, refreshToken = refreshToken, member = member, email = email)
//    }
//}

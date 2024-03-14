package org.spartaa3.movietogather.domain.member.repository


import org.spartaa3.movietogather.domain.member.entity.Member
import org.spartaa3.movietogather.infra.security.jwt.UserPrincipal
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, Int> {

    fun findByEmailAndOAuthType(email: String, oauthType: String): Member?

    fun findByEmail(email: String): Member
}
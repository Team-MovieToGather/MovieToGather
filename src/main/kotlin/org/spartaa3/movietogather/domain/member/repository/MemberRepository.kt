package org.spartaa3.movietogather.domain.member.repository


import org.spartaa3.movietogather.domain.member.entity.Member
import org.spartaa3.movietogather.domain.member.oauth2.OAuth2Provider
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, Long> {

    fun findByEmailAndProvider(email: String, provider: OAuth2Provider): Member

    fun existsByEmailAndProvider(email: String, provider: OAuth2Provider): Boolean

    fun findByEmail(email: String): Member
}
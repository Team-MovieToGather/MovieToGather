package org.spartaa3.movietogather.domain.member.repository

import org.spartaa3.movietogather.domain.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {

    fun findByEmail(email: String): Member?
    fun existsByEmail(email: String): Boolean
}
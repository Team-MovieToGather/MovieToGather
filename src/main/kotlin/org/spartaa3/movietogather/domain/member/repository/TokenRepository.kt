package org.spartaa3.movietogather.domain.member.repository

import org.spartaa3.movietogather.domain.member.entity.Member
import org.spartaa3.movietogather.domain.member.entity.MemberToken
import org.springframework.data.jpa.repository.JpaRepository

interface TokenRepository : JpaRepository<MemberToken, Long> {
    fun findByMember(member: Member): MemberToken?
}
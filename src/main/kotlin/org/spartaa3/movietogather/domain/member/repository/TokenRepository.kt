package org.spartaa3.movietogather.domain.member.repository

import org.spartaa3.movietogather.domain.member.entity.MemberToken
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TokenRepository : JpaRepository<MemberToken, Long> {

}
package org.spartaa3.movietogather.infra.security.refresh

import org.springframework.data.repository.CrudRepository

interface RefreshTokenRepository : CrudRepository<RefreshToken, String> {

    fun findByRefreshToken(refreshToken: String?): RefreshToken?
}
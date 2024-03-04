package org.spartaa3.movietogather.domain.api.service.dto.response

data class GenreResponse(
    val genres: List<Genre>
)

data class Genre(
    val id: Int,
    val name: String
)
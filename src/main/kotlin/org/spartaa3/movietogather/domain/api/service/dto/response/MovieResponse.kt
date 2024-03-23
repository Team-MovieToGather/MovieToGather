package org.spartaa3.movietogather.domain.api.service.dto.response

data class MovieResponse(
    val id: Int,
    val title: String,
    val overview: String,
    val genre_ids: List<Int>,
    val poster_path: String?,
    var genreNames: String = ""
) {
    val posterUrl = if (poster_path != null) {
        "https://image.tmdb.org/t/p/w500$poster_path"
    } else {
        "https://drive.google.com/file/d/1uQKNyT4pyQZaAyhU-xbSS-Qi8jueYkfK/view?usp=sharing"
    }

    val shortOverview: String =
        when {
            overview.isEmpty() -> " ".repeat(60)
            overview.length > 30 -> "Overview: " + overview.substring(0,29) + "..."
            else -> "Overview: $overview"
        }
}

data class MovieListResponse(
    val results: List<MovieResponse>,
)

data class CustomPageResponse(
    val content: List<MovieResponse>,
    val totalPages: Int,
    val totalElements: Int,
    val pageSize: Int,
)


package org.spartaa3.movietogather.domain.api.service.dto.response

data class MovieResponse(
    val id: Int,
    val title: String,
    val genre_ids: List<Int>,
    val poster_path: String?,
    var genreNames: String = ""
) {
    val posterUrl = if (poster_path != null) {
        "https://image.tmdb.org/t/p/w500$poster_path"
    } else {
        "https://drive.google.com/file/d/1uQKNyT4pyQZaAyhU-xbSS-Qi8jueYkfK/view?usp=sharing"
    }
}

data class MovieListResponse(
    val results: List<MovieResponse>,
    val page: Int,
    val total_pages: Int,
    val total_results: Int
)



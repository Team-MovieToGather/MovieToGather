package org.spartaa3.movietogather.domain.api.service.dto.response

data class MovieResponse(
    val id: Int,
    val title: String,
    val genre_ids: List<Int>,
    val poster_path: String,
    var genreNames: String = ""
) {
    val posterUrl = "https://image.tmdb.org/t/p/w500$poster_path"
}

data class MovieListResponse(
    val results: List<MovieResponse>,
    val page: Int,
    val total_pages: Int,
    val total_results: Int
)



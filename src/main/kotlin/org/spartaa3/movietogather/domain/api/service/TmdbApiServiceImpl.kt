package org.spartaa3.movietogather.domain.api.service

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.spartaa3.movietogather.domain.api.service.dto.response.GenreResponse
import org.spartaa3.movietogather.domain.api.service.dto.response.MovieListResponse
import org.spartaa3.movietogather.domain.api.service.dto.response.MovieResponse
import org.spartaa3.movietogather.domain.review.repository.ReviewRepository
import org.spartaa3.movietogather.infra.api.ApiProperties
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.SliceImpl
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient

@Service
class TmdbApiServiceImpl(
    private val apiProperties: ApiProperties,
    private val reviewRepository: ReviewRepository
) : ApiService {
    val restClient: RestClient = RestClient.create()
    val objectMapper =
        jacksonObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false) // 정의되지 않은 필드 무시


    // 영화 호출 api
    override fun getMovies(title: String?, pageNumber: Int): SliceImpl<MovieResponse> {
        var type = if(!title.isNullOrEmpty()) "search" else "popular"
        var url = getBaseUrl(type, pageNumber).let { if (!title.isNullOrEmpty()) "$it&query=$title" else it }

        return getUrlResult(url)
            .let { getMovieResponse(it) }
            .let { matchGenreNames(it) }
    }


    // 호출 url 작성
    private fun getBaseUrl(type: String, pageNumber: Int) =
        when (type) {
            "popular" -> "${apiProperties.popularUrl}"
            "search" -> "${apiProperties.searchUrl}"
            "genre" -> "${apiProperties.genreUrl}"
            else -> "${apiProperties.popularUrl}"
        } + "?${getDefaultUrlParameter(pageNumber)}"


    // Default 요청 인자
    private fun getDefaultUrlParameter(pageNumber: Int): String {
        return "?include_adult=false" +
                "&language=ko-KO" +
                "&include_video=false" +
                "&region=140" +
                "&page=${pageNumber}" +
                "&api_key=${apiProperties.key}"
    }

    // 호출 반환값
    private fun getUrlResult(url: String) = restClient.get()
        .uri(url)
        .retrieve()
        .body(String::class.java)!!

    private fun getMovieResponse(result: String): MovieListResponse {
        return result.let { objectMapper.readValue(it, MovieListResponse::class.java) }
    }

    // 장르명 반환
    private fun matchGenreNames(movieListResponse: MovieListResponse): SliceImpl<MovieResponse> {
        val genreResponse = getGenres()

        movieListResponse.results.forEach { movie ->
            movie.genreNames = movie.genre_ids.mapNotNull { id ->
                genreResponse.genres.find { genre -> genre.id == id }?.name
            }.joinToString(", ")
        }
        return slicePaging(movieListResponse)
    }


    // 장르 목록 호출 -> caching 필요
    private fun getGenres(): GenreResponse {
        val url = getBaseUrl("genre", 1)
       return getUrlResult(url).let { objectMapper.readValue(it, GenreResponse::class.java) }
    }
}


    // Slice 페이징
    private fun slicePaging(movieListResponse: MovieListResponse): SliceImpl<MovieResponse> {
        val currentPage = movieListResponse.page
        val pageSize = 20
        val hasNext = currentPage < movieListResponse.total_pages

        return SliceImpl(
            movieListResponse.results,
            PageRequest.of(currentPage , pageSize), // 페이지 요청 정보
            hasNext
        )
    }

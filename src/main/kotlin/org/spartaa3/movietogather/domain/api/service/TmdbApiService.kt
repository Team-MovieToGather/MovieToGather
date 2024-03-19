package org.spartaa3.movietogather.domain.api.service

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.spartaa3.movietogather.domain.api.service.dto.response.GenreResponse
import org.spartaa3.movietogather.domain.api.service.dto.response.MovieListResponse
import org.spartaa3.movietogather.domain.api.service.dto.response.MovieResponse
import org.spartaa3.movietogather.global.exception.BaseException
import org.spartaa3.movietogather.global.exception.ModelNotFoundException
import org.spartaa3.movietogather.global.exception.dto.BaseResponseCode
import org.spartaa3.movietogather.infra.api.ApiProperties
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.SliceImpl
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import java.lang.RuntimeException

@Service
class TmdbApiService(
    private val apiProperties: ApiProperties
) {
    val restClient: RestClient = RestClient.create()
    val objectMapper: ObjectMapper =
        jacksonObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false) // 정의되지 않은 필드 무시


    // 영화 호출 api
    fun getMovies(title: String?, pageNumber: Int): SliceImpl<MovieResponse> {
        val type = if (!title.isNullOrEmpty()) "search" else "popular"
        val url = getBaseUrl(type, pageNumber).let { if (!title.isNullOrEmpty()) "$it&query=$title" else it }
        val movieListResponse = getMovieResponse(getResult(url))
        if (movieListResponse.results.isEmpty()) throw BaseException(BaseResponseCode.INVALID_TITLE)

        return matchGenreNames(movieListResponse)
    }


    // 호출 url 작성
    private fun getBaseUrl(type: String, pageNumber: Int) =
        when (type) {
            "popular" -> apiProperties.popularUrl
            "search" -> apiProperties.searchUrl
            "genre" -> apiProperties.genreUrl
            else -> apiProperties.popularUrl
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

    // 호출 결과값
    private fun getResult(url: String) = restClient.get()
        .uri(url)
        .retrieve()
        .body(String::class.java)!!

    // DTO 변환
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
        return getResult(url).let { objectMapper.readValue(it, GenreResponse::class.java) }
    }
}


// Slice 페이징
private fun slicePaging(movieListResponse: MovieListResponse): SliceImpl<MovieResponse> {
    val currentPage = movieListResponse.page
    val pageSize = 20
    val hasNext = currentPage < movieListResponse.total_pages

    return SliceImpl(
        movieListResponse.results,
        PageRequest.of(currentPage, pageSize), // 페이지 요청 정보
        hasNext
    )
}

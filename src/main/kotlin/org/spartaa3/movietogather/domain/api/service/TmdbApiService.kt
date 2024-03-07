package org.spartaa3.movietogather.domain.api.service

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.spartaa3.movietogather.domain.api.service.dto.response.GenreResponse
import org.spartaa3.movietogather.domain.api.service.dto.response.MovieListResponse
import org.spartaa3.movietogather.domain.api.service.dto.response.MovieResponse
import org.spartaa3.movietogather.domain.review.repository.ReviewRepository
import org.spartaa3.movietogather.global.exception.ModelNotFoundException
import org.spartaa3.movietogather.infra.api.ApiProperties
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.SliceImpl
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient

@Service
class TmdbApiService (
    private val apiProperties: ApiProperties,
    private val reviewRepository: ReviewRepository
): ApiService {
    val restClient: RestClient = RestClient.create()
    val objectMapper = jacksonObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false) // 정의되지 않은 필드 무시


    // 인기 영화 데이터 목록 호출
    override fun getPopularMoviesList(page: Int): SliceImpl<MovieResponse> {
        val url = getUrl(page)

        val result = restClient.get()
            .uri(url)
            .retrieve()
            .body(String::class.java)
            ?: throw ModelNotFoundException("movie", 1) // 예외처리항목 -> 다른 이슈로 정리


        val response: MovieListResponse = result.let { objectMapper.readValue(it, MovieListResponse::class.java)}

        return matchGenreNames(response)
    }

    // url 작성 (확장 시 프로퍼티로 받아 사용)
    private fun getUrl(page: Int): String {
        return "${apiProperties.popularUrl}" +
                "?${getDefaultUrlParameter()}" +
                "&page=${page}" +
                "&api_key=${apiProperties.key}"
    }

    // Default 요청 인자
    private fun getDefaultUrlParameter(): String {
        return "?include_adult=false" +
                "&language=ko-KO" +
                "&include_video=false" +
                "&region=140"
    }


    // 장르 id 기반 장르명 반환
    private fun matchGenreNames(movieListResponse: MovieListResponse): SliceImpl<MovieResponse> {
        val genreResponse = getGenres()

//        movieListResponse.results.forEach {
//            it.genreNames.addAll(
//                it.genre_ids.mapNotNull { id ->
//                    genreResponse.genres.find {
//                        it.id == id
//                    }?.name
//                }
//            )
//        }
        movieListResponse.results.forEach { movie ->
            movie.genreNames = movie.genre_ids.mapNotNull { id ->
                genreResponse.genres.find { genre -> genre.id == id }?.name
            }.joinToString(", ") // 여러 장르명을 쉼표로 구분하여 하나의 문자열로 결합
        }
        return slicePaging(movieListResponse)
    }


    // 장르 목록 호출
    private fun getGenres() = restClient.get()
            .uri("${apiProperties.genreUrl}" +
                    "?language=ko" +
                    "&api_key=${apiProperties.key}")
            .retrieve()
            .body(String::class.java)
            ?.let { objectMapper.readValue(it, GenreResponse::class.java)}
            ?: throw ModelNotFoundException("movie", 1) // 예외처리항목 -> 다른 이슈로 정리


    // Slice 페이징
    // 기존 함수명 SlicePaging -> slicePaging 으로 변경
    private fun slicePaging(movieListResponse: MovieListResponse): SliceImpl<MovieResponse> {
        val hasNext: Boolean = movieListResponse.page != movieListResponse.total_pages
        return SliceImpl(movieListResponse.results, PageRequest.of(movieListResponse.page, movieListResponse.total_pages), hasNext)
    }
}
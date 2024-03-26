package org.spartaa3.movietogather.domain.api.service

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.spartaa3.movietogather.domain.api.service.dto.response.CustomPageResponse
import org.spartaa3.movietogather.domain.api.service.dto.response.GenreResponse
import org.spartaa3.movietogather.domain.api.service.dto.response.MovieListResponse
import org.spartaa3.movietogather.global.exception.BaseException
import org.spartaa3.movietogather.global.exception.dto.BaseResponseCode
import org.spartaa3.movietogather.infra.api.ApiProperties
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import java.util.concurrent.TimeUnit

@Service
class TmdbApiService(
    private val apiProperties: ApiProperties,
    private val redisTemplate: RedisTemplate<String, String>
) {
    val restClient: RestClient = RestClient.create()
    val objectMapper: ObjectMapper =
        jacksonObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false) // 정의되지 않은 필드 무시


    // 영화 호출 api
    fun getMovies(title: String?): CustomPageResponse {
        val cacheKey = "movies::$title"
        val movieJson = redisTemplate.opsForValue().get(cacheKey)
        if (movieJson != null) {
            return objectMapper.readValue(movieJson)
        }

        val type = if (!title.isNullOrEmpty()) "search" else "popular"
        val url = getBaseUrl(type).let { if (!title.isNullOrEmpty()) "$it&query=$title" else it }
        val movieListResponse = getMovieResponse(getResult(url))
        if (movieListResponse.results.isEmpty()) throw BaseException(BaseResponseCode.INVALID_TITLE)
        val response = matchGenreNames(movieListResponse)
        val responseJson = objectMapper.writeValueAsString(response)

        val ttl = if (title.isNullOrEmpty()) 7L else 1L // 인기 목록은 7일 그 외는 1일
        redisTemplate.opsForValue().set(cacheKey, responseJson, ttl, TimeUnit.DAYS)

        return response
    }


    // 호출 url 작성
    private fun getBaseUrl(type: String) =
        when (type) {
            "popular" -> apiProperties.popularUrl
            "search" -> apiProperties.searchUrl
            "genre" -> apiProperties.genreUrl
            else -> apiProperties.popularUrl
        } + "?${getDefaultUrlParameter()}"


    // Default 요청 인자
    private fun getDefaultUrlParameter(): String {
        return "?include_adult=false" +
                "&language=ko-KO" +
                "&include_video=false" +
                "&region=140" +
                "&page=1" +
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
    private fun matchGenreNames(movieListResponse: MovieListResponse): CustomPageResponse {
        val genreResponse = getGenres()

        movieListResponse.results.forEach { movie ->
            movie.genreNames = movie.genre_ids.mapNotNull { id ->
                genreResponse.genres.find { genre -> genre.id == id }?.name
            }.joinToString(", ")
        }
        return CustomPageResponse(
            movieListResponse.results,
            3,
            20,
            9
        )
    }


    // 장르 목록 호출 -> caching 필요
    private fun getGenres(): GenreResponse {
        val url = getBaseUrl("genre")
        return getResult(url).let { objectMapper.readValue(it, GenreResponse::class.java) }
    }
}




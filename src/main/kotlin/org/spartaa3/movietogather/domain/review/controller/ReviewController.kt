package org.spartaa3.movietogather.domain.review.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.spartaa3.movietogather.domain.api.service.TmdbApiService
import org.spartaa3.movietogather.domain.api.service.dto.response.CustomPageResponse
import org.spartaa3.movietogather.domain.review.dto.CreateReviewRequest
import org.spartaa3.movietogather.domain.review.dto.ReviewResponse
import org.spartaa3.movietogather.domain.review.dto.ReviewsResponse
import org.spartaa3.movietogather.domain.review.dto.UpdateReviewRequest
import org.spartaa3.movietogather.domain.review.entity.ReviewSearchCondition
import org.spartaa3.movietogather.domain.review.service.ReviewService
import org.spartaa3.movietogather.infra.security.jwt.UserPrincipal
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@Tag(name = "review", description = "Review API")
@RequestMapping("/api/reviews")
@RestController
class ReviewController(
    private val reviewService: ReviewService,
    private val tmdbApiService: TmdbApiService
) {
    @GetMapping("/bestTop3")
    fun bestTopReview(): ResponseEntity<List<ReviewsResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(reviewService.bestTopReview())
    }

    @GetMapping("/search")
    fun searchReview(
        @RequestParam(name = "searchCondition") condition: ReviewSearchCondition,
        @RequestParam(name = "keyword") keyword: String?,
        pageable: Pageable
    ): ResponseEntity<Page<ReviewsResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(reviewService.searchReview(condition, keyword, pageable))
    }

    @GetMapping("/{reviewId}")
    fun getReviewById(
        @PathVariable reviewId: Long
    ): ResponseEntity<ReviewResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(reviewService.getReviewById(reviewId))
    }

    @PostMapping
    fun createReview(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestBody request: CreateReviewRequest
    ): ResponseEntity<ReviewResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(reviewService.createReview(userPrincipal.email, request))
    }

    @PutMapping("/{reviewId}")
    fun updateReview(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestBody request: UpdateReviewRequest,
        @PathVariable reviewId: Long
    ): ResponseEntity<ReviewResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(reviewService.updateReview(userPrincipal.email, reviewId, request))
    }

    @DeleteMapping("/{reviewId}")
    fun deleteReview(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable reviewId: Long
    ): ResponseEntity<Unit> {
        reviewService.deleteReview(userPrincipal.email, reviewId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }

    // 영화 호출
    @GetMapping("/movies")
    fun getMovies(
        @RequestParam title: String? = null
    ): ResponseEntity<CustomPageResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(tmdbApiService.getMovies(title))
    }
}
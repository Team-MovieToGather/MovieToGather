package org.spartaa3.movietogather.domain.review.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.spartaa3.movietogather.domain.review.dto.CreateReviewRequest
import org.spartaa3.movietogather.domain.review.dto.ReviewResponse
import org.spartaa3.movietogather.domain.review.dto.UpdateReviewRequest
import org.spartaa3.movietogather.domain.review.service.ReviewService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "review", description = "Review API")
@RequestMapping("/api/reviews")
@RestController
class ReviewController(
    private val reviewService: ReviewService
) {

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
        @RequestBody request: CreateReviewRequest
    ): ResponseEntity<ReviewResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(reviewService.createReview(request))
    }

    @PutMapping("/{reviewId}")
    fun updateReview(
        @RequestBody request: UpdateReviewRequest,
        @PathVariable reviewId: Long
    ): ResponseEntity<ReviewResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(reviewService.updateReview(reviewId, request))
    }

    @DeleteMapping("/{reviewId}")
    fun deleteReview(
        @PathVariable reviewId: Long
    ): ResponseEntity<Unit> {
        reviewService.deleteReview(reviewId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }
}

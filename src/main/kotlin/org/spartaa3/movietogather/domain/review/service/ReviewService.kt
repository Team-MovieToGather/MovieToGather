package org.spartaa3.movietogather.domain.review.service

import org.spartaa3.movietogather.domain.review.dto.CreateReviewRequest
import org.spartaa3.movietogather.domain.review.dto.ReviewResponse
import org.spartaa3.movietogather.domain.review.dto.ReviewsResponse
import org.spartaa3.movietogather.domain.review.dto.UpdateReviewRequest
import org.spartaa3.movietogather.domain.review.entity.ReviewSearchCondition
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ReviewService {
    fun bestTopReview(): List<ReviewsResponse>

    fun searchReview(condition: ReviewSearchCondition, keyword: String?, pageable: Pageable): Page<ReviewsResponse>

    fun getReviewById(reviewId: Long): ReviewResponse

    fun createReview(email: String, request: CreateReviewRequest): ReviewResponse

    fun updateReview(email: String, reviewId: Long, request: UpdateReviewRequest): ReviewResponse

    fun deleteReview(email: String, reviewId: Long)
}
package org.spartaa3.movietogather.domain.review.service

import org.spartaa3.movietogather.domain.review.dto.CreateReviewRequest
import org.spartaa3.movietogather.domain.review.dto.ReviewResponse
import org.spartaa3.movietogather.domain.review.dto.ReviewsResponse
import org.spartaa3.movietogather.domain.review.dto.UpdateReviewRequest
import org.spartaa3.movietogather.domain.review.entity.ReviewSearchCondition
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface ReviewService {
    fun bestTopReview(): List<ReviewsResponse>

    fun searchReview(condition: ReviewSearchCondition, keyword: String?, pageable: Pageable): Page<ReviewsResponse>

    fun getReviewById(reviewId: Long): ReviewResponse

    fun createReview(request: CreateReviewRequest): ReviewResponse    //나중에 userprincipal 추가

    fun updateReview(reviewId: Long, request: UpdateReviewRequest): ReviewResponse   //나중에 userprincipal 추가

    fun deleteReview(reviewId: Long)    //나중에 userprincipal 추가
}
package org.spartaa3.movietogather.domain.review.service

import org.spartaa3.movietogather.domain.review.dto.CreateReviewRequest
import org.spartaa3.movietogather.domain.review.dto.ReviewResponse
import org.spartaa3.movietogather.domain.review.dto.UpdateReviewRequest
import org.spartaa3.movietogather.domain.review.entity.Review
import org.spartaa3.movietogather.domain.review.entity.ReviewSearchCondition
import org.spartaa3.movietogather.domain.review.entity.toResponse
import org.spartaa3.movietogather.domain.review.repository.ReviewRepository
import org.spartaa3.movietogather.global.exception.ReviewNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class ReviewServiceImpl(
    private val reviewRepository: ReviewRepository
) : ReviewService {
    override fun searchReview(
        condition: ReviewSearchCondition,
        keyword: String?,
        pageable: Pageable
    ): Page<ReviewResponse> {
        return reviewRepository.searchReview(condition, keyword, pageable).map { it.toResponse() }
    }

    override fun getReviewById(reviewId: Long): ReviewResponse {
        val review = reviewRepository.findByIdOrNull(reviewId) ?: throw ReviewNotFoundException("Review", reviewId)
        return review.toResponse()
    }

    @Transactional
    override fun createReview(request: CreateReviewRequest): ReviewResponse {
        return reviewRepository.save<Review>(
            Review(
                postingTitle = request.postingTitle,
                star = request.star,
                movieTitle = "", // 선택된 영화 처리 작업 후 수정 예정
                movieImg = "",
                contents = request.contents,
                genre = ""
            )
        ).toResponse()
    }

    @Transactional
    override fun updateReview(reviewId: Long, request: UpdateReviewRequest): ReviewResponse {
        val review = reviewRepository.findByIdOrNull(reviewId) ?: throw ReviewNotFoundException("Review", reviewId)
        val (postingTitle, star, contents) = request

        review.postingTitle = postingTitle
        review.star = star
//        review.movieTitle = movieTitle
//        review.movieImg = movieImg
        review.contents = contents
//        review.genre = genre
        return reviewRepository.save(review).toResponse()
    }

    override fun deleteReview(reviewId: Long) {
        val review = reviewRepository.findByIdOrNull(reviewId) ?: throw ReviewNotFoundException("Review", reviewId)
        reviewRepository.delete(review)
    }
}
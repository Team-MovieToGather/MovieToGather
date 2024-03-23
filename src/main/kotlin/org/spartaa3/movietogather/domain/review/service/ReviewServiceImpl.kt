package org.spartaa3.movietogather.domain.review.service

import org.spartaa3.movietogather.domain.review.dto.CreateReviewRequest
import org.spartaa3.movietogather.domain.review.dto.ReviewResponse
import org.spartaa3.movietogather.domain.review.dto.ReviewsResponse
import org.spartaa3.movietogather.domain.review.dto.UpdateReviewRequest
import org.spartaa3.movietogather.domain.review.entity.Review
import org.spartaa3.movietogather.domain.review.entity.ReviewSearchCondition
import org.spartaa3.movietogather.domain.review.entity.toResponse
import org.spartaa3.movietogather.domain.review.repository.HeartRepository
import org.spartaa3.movietogather.domain.review.repository.RedisRepository
import org.spartaa3.movietogather.domain.review.repository.ReviewRepository
import org.spartaa3.movietogather.global.exception.ReviewNotFoundException
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class ReviewServiceImpl(
    private val reviewRepository: ReviewRepository,
    private val heartRepository: HeartRepository,
    private val redisRepository: RedisRepository
) : ReviewService {
    override fun bestTopReview(): List<ReviewsResponse> {
        val bestReviews = redisRepository.getBestReviews()
        return if (bestReviews != null) bestReviews.map { ReviewsResponse.to(it) }
        else {
            val reviews = reviewRepository.findAll()
            reviews.forEach { it.heart = heartRepository.countHeartByReviewAndCommentsIsNull(it) }
            val bestReviewsFromDB = reviews.sortedByDescending { it.heart }.take(3)
            redisRepository.saveBestReviews(bestReviewsFromDB)
            bestReviewsFromDB.map { ReviewsResponse.to(it) }
        }
    }

    override fun searchReview(
        condition: ReviewSearchCondition,
        keyword: String?,
        pageable: Pageable
    ): Slice<ReviewsResponse> {
        val reviews = reviewRepository.searchReview(condition, keyword, pageable)
        reviews.forEach { it.heart = heartRepository.countHeartByReviewAndCommentsIsNull(it) }
        return reviews.map { ReviewsResponse.to(it) }
    }

    override fun getReviewById(reviewId: Long): ReviewResponse {
        val review = reviewRepository.findByIdOrNull(reviewId) ?: throw ReviewNotFoundException("Review", reviewId)
        review.heart = heartRepository.countHeartByReviewAndCommentsIsNull(review)
        review.comments.forEach { it.likeCount = heartRepository.countHeartByReviewAndComments(it.review, it) }
        return review.toResponse()
    }

    @Transactional
    override fun createReview(request: CreateReviewRequest): ReviewResponse {
        return reviewRepository.save<Review>(
            Review(
                postingTitle = request.postingTitle,
                star = request.star,
                movieTitle = request.movieTitle,
                movieImg = request.movieImg,
                contents = request.contents,
                genre = request.genre
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
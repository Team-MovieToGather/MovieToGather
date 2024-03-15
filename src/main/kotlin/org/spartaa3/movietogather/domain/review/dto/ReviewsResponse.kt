package org.spartaa3.movietogather.domain.review.dto

import org.spartaa3.movietogather.domain.review.entity.Review
import java.time.LocalDateTime

data class ReviewsResponse(
    val id: Long,
    val postingTitle: String,
    val genre: String,
    val movieTitle: String,
    val movieImg: String,
    val contents: String,
    val createdAt: LocalDateTime,
    val heart: Int
) {
    companion object {
        fun to(review: Review): ReviewsResponse {
            return ReviewsResponse(
                id = review.id!!,
                postingTitle = review.postingTitle,
                genre = review.genre,
                movieTitle = review.movieTitle,
                movieImg = review.movieImg,
                contents = review.contents,
                createdAt = review.createdAt,
                heart = review.heart
            )
        }
    }
}

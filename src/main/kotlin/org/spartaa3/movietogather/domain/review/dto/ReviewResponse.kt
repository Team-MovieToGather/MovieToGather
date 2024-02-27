package org.spartaa3.movietogather.domain.review.dto

import java.time.LocalDateTime

data class ReviewResponse(
    val id: Long,
    val postingTitle: String,
    val genre: String,
    val star: Double,
    val movieTitle: String,
    val movieImg: String,
    val contents: String,
    val createdAt: LocalDateTime
//    val comments: List<CommentResponse>   //리뷰 추가
)

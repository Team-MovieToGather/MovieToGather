package org.spartaa3.movietogather.domain.review.dto

data class CreateReviewRequest (
    val postingTitle: String,
    val star: Double,
    val movieTitle: String,
    val movieImg: String,
    val contents: String,
    val genre: String
)

data class UpdateReviewRequest (
    val postingTitle: String,
    val star: Double,
    val movieTitle: String,
    val movieImg: String,
    val contents: String,
    val genre: String
)
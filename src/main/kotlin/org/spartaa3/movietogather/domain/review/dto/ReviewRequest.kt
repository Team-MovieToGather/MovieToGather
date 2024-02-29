package org.spartaa3.movietogather.domain.review.dto

data class CreateReviewRequest(
    val postingTitle: String,
    val star: Double,
//    val movieTitle: String, // 선택된 영화 처리 작업 후 수정 예정
//    val movieImg: String,
    val contents: String,
//    val genre: String
)

data class UpdateReviewRequest(
    val postingTitle: String,
    val star: Double,
//    val movieTitle: String,
//    val movieImg: String,
    val contents: String,
//    val genre: String
)
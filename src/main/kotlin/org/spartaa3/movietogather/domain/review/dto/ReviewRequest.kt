package org.spartaa3.movietogather.domain.review.dto

import com.fasterxml.jackson.annotation.JsonCreator

data class CreateReviewRequest @JsonCreator(mode = JsonCreator.Mode.PROPERTIES) constructor(
    val movieTitle: String, // 선택된 영화 처리 작업 후 수정 예정
    val movieImg: String,
    val genre: String,
    val postingTitle: String,
    val star: Double,
    val contents: String,
)

data class UpdateReviewRequest @JsonCreator(mode = JsonCreator.Mode.PROPERTIES) constructor(
    val postingTitle: String,
    val star: Double,
//    val movieTitle: String,
//    val movieImg: String,
    val contents: String,
//    val genre: String
)
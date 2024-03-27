package org.spartaa3.movietogather.global.exception

//변수명 PostingTitle을 postingTitle로 변경
data class ReviewNotFoundException(val postingTitle: String, val id: Long?) :
    RuntimeException("Review $postingTitle not found with given id: $id")

//변수명 ModelName을 ModelName으로 변경
data class ModelNotFoundException(val modelName: String, val id: Long?) :
    RuntimeException("Model $modelName not found with given id: $id")

data class TokenNotFoundException(val modelName: String):
        RuntimeException("Token not found")
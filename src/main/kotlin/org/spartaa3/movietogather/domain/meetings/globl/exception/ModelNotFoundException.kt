package org.spartaa3.movietogather.domain.meetings.globl.exception

data class MeetingsNotFoundException(val postingTitle: String, val id: Long?) :
    RuntimeException("Review $postingTitle not found with given id: $id")

data class ModelNotFoundException(val modelName: String, val id: Long?) :
    RuntimeException("Model $modelName not found with given id: $id")
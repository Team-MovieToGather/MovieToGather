package org.spartaa3.movietogather.domain.meetings.globl.exception

data class MeetingsNotFoundException(val PostingTitle: String, val id: Long?) :
    RuntimeException("Review $PostingTitle not found with given id: $id")

data class ModelNotFoundException(val ModelName: String, val id: Long?) :
    RuntimeException("Model $ModelName not found with given id: $id")
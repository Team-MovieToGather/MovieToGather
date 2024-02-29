package org.spartaa3.movietogather.global.exception

import org.spartaa3.movietogather.global.exception.dto.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(ModelNotFoundException::class)
    fun handleModelNotFoundException(e: ModelNotFoundException): ResponseEntity<ErrorResponse> {
        return status(HttpStatus.NOT_FOUND).body(ErrorResponse(message = e.message))
    }

    @ExceptionHandler(ReviewNotFoundException::class)
    fun handleReviewNotFoundException(e: ReviewNotFoundException): ResponseEntity<ErrorResponse> {
        return status(HttpStatus.NOT_FOUND).body(ErrorResponse(message = e.message))
    }
}
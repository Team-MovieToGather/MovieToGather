package org.spartaa3.movietogather.domain.review.controller

import org.spartaa3.movietogather.domain.review.dto.HeartResponse
import org.spartaa3.movietogather.domain.review.service.HeartService
import org.spartaa3.movietogather.infra.security.jwt.UserPrincipal
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/reviews/{reviewId}")
class HeartController(
    private val heartService: HeartService
) {
    @PostMapping("/heart")
    fun reviewHeart(
        @PathVariable reviewId: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<HeartResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(heartService.reviewHeart(reviewId, userPrincipal.id))
    }

    @PostMapping("/comments/{commentsId}")
    fun commentHeart(
        @PathVariable reviewId: Long,
        @PathVariable commentsId: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<HeartResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(heartService.commentHeart(reviewId, userPrincipal.id, commentsId))
    }
}
package org.spartaa3.movietogather.domain.comments.controller

import org.spartaa3.movietogather.domain.comments.dto.commentsRequest.CreateCommentsRequest
import org.spartaa3.movietogather.domain.comments.dto.commentsRequest.UpdateCommentsRequest
import org.spartaa3.movietogather.domain.comments.dto.commentsResponse.GetCommentsResponse
import org.spartaa3.movietogather.domain.comments.service.CommentsService
import org.spartaa3.movietogather.infra.security.jwt.UserPrincipal
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/reviews")
@RestController
class CommentsController(
    private val commentsService: CommentsService
) {

    @GetMapping("/{reviewId}/comments/{commentsId}")
    fun getCommentsById(
        @PathVariable reviewId: Long,
        @PathVariable commentsId: Long
    ): ResponseEntity<GetCommentsResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentsService.getCommentsById(reviewId, commentsId))
    }

    @PostMapping("/{reviewId}/comments")
    fun createComments(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable reviewId: Long,
        @RequestBody request: CreateCommentsRequest
    ): ResponseEntity<String> {
        commentsService.createComments(userPrincipal.email, reviewId, request)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body("댓글이 등록되었습니다..")
    }

    @PutMapping("/{reviewId}/comments/{commentsId}")
    fun updateComments(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestBody request: UpdateCommentsRequest,
        @PathVariable commentsId: Long,
        @PathVariable reviewId: Long,
    ): ResponseEntity<String> {
        commentsService.updateComments(userPrincipal.email, reviewId, commentsId, request)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("댓글이 수정되었습니다..")
    }

    @DeleteMapping("/{reviewId}/comments/{commentsId}")
    fun deleteComments(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable commentsId: Long,
        @PathVariable reviewId: Long
    ): ResponseEntity<String> {
        commentsService.deleteComments(userPrincipal.email, reviewId, commentsId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body("댓글이 삭제되었습니다..")
    }
}
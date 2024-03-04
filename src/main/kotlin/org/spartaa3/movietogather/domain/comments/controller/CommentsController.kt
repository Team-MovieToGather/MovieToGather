package org.spartaa3.movietogather.domain.comments.controller

import org.spartaa3.movietogather.domain.comments.dto.commentsRequest.CreateCommentsRequest
import org.spartaa3.movietogather.domain.comments.dto.commentsRequest.UpdatecommentsRequest
import org.spartaa3.movietogather.domain.comments.dto.commentsResponse.GetCommentsResponse
import org.spartaa3.movietogather.domain.comments.service.CommentsService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/reviews")
@RestController
class CommentsController(
    private val commentsService: CommentsService
) {

    @GetMapping("/review/{reviewId}/comments/{commentsId}")
    fun getCommentsById(
        @PathVariable reviewId: Long,
        @PathVariable commentsId: Long
    ): ResponseEntity<GetCommentsResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentsService.getCommentsById(reviewId, commentsId))
    }

    @PostMapping("/review/{reviewId}/comments")
    fun createComments(
        @PathVariable reviewId: Long,
        @RequestBody request: CreateCommentsRequest
    ): ResponseEntity<String> {
        commentsService.createComments(reviewId, request)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body("댓글이 등록되었습니다..")
    }

    @PutMapping("/review/{reviewId}/comments/{commentsId}")
    fun updateComments(
        @RequestBody request: UpdatecommentsRequest,
        @PathVariable commentsId: Long,
        @PathVariable reviewId: Long,
    ): ResponseEntity<String> {
        commentsService.updateComments(reviewId, commentsId, request)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("댓글이 수정되었습니다..")
    }

    @DeleteMapping("/review/{reviewId}/comments/{commentsId}")
    fun deleteComments(
        @PathVariable commentsId: Long,
        @PathVariable reviewId: Long
    ): ResponseEntity<String> {
        commentsService.deleteComments(reviewId, commentsId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body("댓글이 삭제되었습니다..")
    }
}
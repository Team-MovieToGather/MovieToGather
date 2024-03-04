package org.spartaa3.movietogather.domain.comments.service

import org.spartaa3.movietogather.domain.comments.dto.commentsRequest.CreateCommentsRequest
import org.spartaa3.movietogather.domain.comments.dto.commentsRequest.UpdatecommentsRequest
import org.spartaa3.movietogather.domain.comments.dto.commentsResponse.GetCommentsResponse
import org.spartaa3.movietogather.domain.comments.entity.Comments
import org.spartaa3.movietogather.domain.comments.repository.CommentsRepository
import org.spartaa3.movietogather.domain.review.repository.ReviewRepository
import org.spartaa3.movietogather.global.exception.ReviewNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


@Service
class CommentsServiceImpl(
    private val commentsRepository: CommentsRepository,
    private val reviewRepository: ReviewRepository
) : CommentsService {
    override fun getCommentsById(reviewId: Long, commentsId: Long): GetCommentsResponse {
        reviewRepository.findByIdOrNull(reviewId) ?: throw ReviewNotFoundException("Review", reviewId)
        val comments =
            commentsRepository.findByIdOrNull(commentsId) ?: throw ReviewNotFoundException("Comments", commentsId)
        return comments.let { GetCommentsResponse.from(it) }
    }

    @Transactional
    override fun createComments(reviewId: Long, request: CreateCommentsRequest): GetCommentsResponse {
        val review = reviewRepository.findByIdOrNull(reviewId) ?: throw ReviewNotFoundException("Review", reviewId)
        val comments = commentsRepository.save(
            Comments(
                contents = request.contents,
                likeCount = 0L,
                createdAt = LocalDateTime.now(),
                createdBy = "작성자",
                modifiedAt = LocalDateTime.now(),
                isDeleted = false,
                review = review
            )
        )
        return comments.let { GetCommentsResponse.from(it) }
    }

    @Transactional
    override fun updateComments(reviewId: Long, commentsId: Long, request: UpdatecommentsRequest): GetCommentsResponse {
        val review = reviewRepository.findByIdOrNull(reviewId) ?: throw ReviewNotFoundException("Review", reviewId)
        val comments =
            commentsRepository.findByIdOrNull(commentsId) ?: throw ReviewNotFoundException("Comments", commentsId)
        comments.contents = request.contents
        return comments.let { GetCommentsResponse.from(it) }
    }

    @Transactional
    override fun deleteComments(reviewId: Long, commentsId: Long) {
        val review = reviewRepository.findByIdOrNull(reviewId) ?: throw ReviewNotFoundException("Review", reviewId)
        val comments =
            commentsRepository.findByIdOrNull(commentsId) ?: throw ReviewNotFoundException("Comments", commentsId)
        commentsRepository.delete(comments)
    }
}
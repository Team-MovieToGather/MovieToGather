package org.spartaa3.movietogather.domain.comments.service

import org.spartaa3.movietogather.domain.comments.dto.commentsRequest.CreateCommentsRequest
import org.spartaa3.movietogather.domain.comments.dto.commentsRequest.UpdateCommentsRequest
import org.spartaa3.movietogather.domain.comments.dto.commentsResponse.GetCommentsResponse

interface CommentsService {

    fun getCommentsById(reviewId: Long, commentsId: Long): GetCommentsResponse

    fun createComments(email: String, reviewId: Long, request: CreateCommentsRequest): GetCommentsResponse

    fun updateComments(
        email: String,
        reviewId: Long,
        commentsId: Long,
        request: UpdateCommentsRequest
    ): GetCommentsResponse

    fun deleteComments(email: String, reviewId: Long, commentsId: Long)
}

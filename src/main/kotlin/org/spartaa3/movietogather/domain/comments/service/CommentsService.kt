package org.spartaa3.movietogather.domain.comments.service

import org.spartaa3.movietogather.domain.comments.dto.commentsRequest.CreateCommentsRequest
import org.spartaa3.movietogather.domain.comments.dto.commentsRequest.UpdateCommentsRequest
import org.spartaa3.movietogather.domain.comments.dto.commentsResponse.GetCommentsResponse

interface CommentsService {

    fun getCommentsById(reviewId: Long, commentsId: Long): GetCommentsResponse

    fun createComments(reviewId: Long, request: CreateCommentsRequest): GetCommentsResponse    //나중에 userprincipal 추가

    fun updateComments(
        reviewId: Long,
        commentsId: Long,
        request: UpdateCommentsRequest
    ): GetCommentsResponse  //나중에 userprincipal 추가

    fun deleteComments(reviewId: Long, commentsId: Long)    //나중에 userprincipal 추가
}

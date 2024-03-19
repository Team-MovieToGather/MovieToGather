package org.spartaa3.movietogather.domain.comments.repository


import org.spartaa3.movietogather.domain.comments.entity.Comments
import org.springframework.data.jpa.repository.JpaRepository

interface CommentsRepository : JpaRepository<Comments, Long> {
    fun findByIdAndReviewId(commentsId: Long, reviewId: Long): Comments
}
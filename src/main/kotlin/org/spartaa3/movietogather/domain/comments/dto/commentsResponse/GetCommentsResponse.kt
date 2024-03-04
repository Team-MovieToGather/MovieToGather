package org.spartaa3.movietogather.domain.comments.dto.commentsResponse

import org.spartaa3.movietogather.domain.comments.entity.Comments
import java.time.LocalDateTime

data class GetCommentsResponse(
    val id: Long?,
    val contents: String,
    val likeCount: Long,
    val createdAt: LocalDateTime,
    val createdBy: String,
) {
    companion object {
        fun from(comments: Comments): GetCommentsResponse {
            return GetCommentsResponse(
                id = comments.id,
                contents = comments.contents,
                likeCount = comments.likeCount,
                createdAt = comments.createdAt,
                createdBy = comments.createdBy
            )
        }
    }
}
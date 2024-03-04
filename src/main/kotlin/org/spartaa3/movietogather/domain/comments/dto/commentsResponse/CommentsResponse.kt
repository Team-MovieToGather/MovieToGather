package org.spartaa3.movietogather.domain.comments.dto.commentsResponse

import java.time.LocalDateTime

class CommentsResponse(
    val id: Long,
    val contents: String,
    val createdAt: LocalDateTime,
)
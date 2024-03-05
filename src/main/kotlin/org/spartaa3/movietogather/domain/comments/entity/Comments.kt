package org.spartaa3.movietogather.domain.comments.entity

import jakarta.persistence.*
import org.spartaa3.movietogather.domain.comments.dto.commentsResponse.CommentsResponse
import org.spartaa3.movietogather.domain.review.entity.Review
import java.time.LocalDateTime

@Entity
@Table(name = "comments")
class Comments(

    @Column(name = "contents")
    var contents: String,
    @Column(name = "like_count")
    var likeCount: Int,

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "created_by")
    var createdBy: String,

    @Column(name = "modified_at")
    val modifiedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "is_deleted")
    val isDeleted: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review")
    val review: Review

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

fun Comments.toResponse(): CommentsResponse {
    return CommentsResponse(
        id = id!!,
        contents = contents,
        createdAt = LocalDateTime.now()
    )
}
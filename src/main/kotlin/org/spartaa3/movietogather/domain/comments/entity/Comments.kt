package org.spartaa3.movietogather.domain.comments.entity

import jakarta.persistence.*
import org.spartaa3.movietogather.domain.review.entity.Review
import org.spartaa3.movietogather.infra.audit.BaseTimeEntity
import org.spartaa3.movietogather.infra.audit.BaseUserEntity

@Entity
@Table(name = "comments")
class Comments(

    @Column(name = "contents")
    var contents: String,
    @Column(name = "like_count")
    var likeCount: Int,

    @Column(name = "is_deleted")
    val isDeleted: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review")
    val review: Review

) : BaseUserEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

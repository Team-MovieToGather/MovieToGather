package org.spartaa3.movietogather.domain.review.entity

import jakarta.persistence.*
import org.spartaa3.movietogather.domain.comments.entity.Comments
import org.spartaa3.movietogather.domain.member.entity.Member

@Entity
data class Heart(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @ManyToOne @JoinColumn(name = "member_id") val member: Member,
    @ManyToOne @JoinColumn(name = "review_id") val review: Review,
    @ManyToOne @JoinColumn(name = "comments_id") val comments: Comments?
)

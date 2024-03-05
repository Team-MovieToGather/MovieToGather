package org.spartaa3.movietogather.domain.review.repository

import org.spartaa3.movietogather.domain.comments.entity.Comments
import org.spartaa3.movietogather.domain.member.entity.Member
import org.spartaa3.movietogather.domain.review.entity.Heart
import org.spartaa3.movietogather.domain.review.entity.Review
import org.springframework.data.jpa.repository.JpaRepository

interface HeartRepository : JpaRepository<Heart, Long> {
    // review
    fun deleteByMemberAndReviewAndCommentsIsNull(member: Member, review: Review)
    fun findByMemberAndReviewAndCommentsIsNull(member: Member, review: Review): Heart?
    fun countHeartByReviewAndCommentsIsNull(review: Review): Int

    // comments
    fun deleteByMemberAndReviewAndComments(member: Member, review: Review, comments: Comments)
    fun findByMemberAndReviewAndComments(member: Member, review: Review, comments: Comments): Heart?
    fun countHeartByReviewAndComments(review: Review, comments: Comments): Int
}
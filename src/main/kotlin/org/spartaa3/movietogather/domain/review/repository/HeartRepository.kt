package org.spartaa3.movietogather.domain.review.repository

import org.spartaa3.movietogather.domain.member.entity.Member
import org.spartaa3.movietogather.domain.review.entity.Heart
import org.spartaa3.movietogather.domain.review.entity.Review
import org.springframework.data.jpa.repository.JpaRepository

interface HeartRepository : JpaRepository<Heart, Long> {
    fun deleteByMemberAndReview(member : Member, review: Review)
    fun findByMemberAndReview(member : Member, review: Review): Heart?
    fun countHeartByReview(review: Review): Int
}
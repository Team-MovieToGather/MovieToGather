package org.spartaa3.movietogather.domain.review.service

import jakarta.transaction.Transactional
import org.spartaa3.movietogather.domain.member.repository.MemberRepository
import org.spartaa3.movietogather.domain.review.dto.HeartResponse
import org.spartaa3.movietogather.domain.review.entity.Heart
import org.spartaa3.movietogather.domain.review.repository.HeartRepository
import org.spartaa3.movietogather.domain.review.repository.ReviewRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class HeartService(
    private val memberRepository: MemberRepository,
    private val reviewRepository: ReviewRepository,
    private val heartRepository: HeartRepository
) {
    @Transactional
    fun reviewHeart(reviewId: Long, memberId: Long): HeartResponse {
        val member = memberRepository.findByIdOrNull(memberId) ?: throw IllegalArgumentException()
        val review = reviewRepository.findByIdOrNull(reviewId) ?: throw IllegalArgumentException()

        val existingHeart = heartRepository.findByMemberAndReview(member, review)

        return if (existingHeart != null) {
            heartRepository.deleteByMemberAndReview(member, review)
            reviewRepository.save(review)
            HeartResponse(message = "좋아요 취소")
        } else {
            heartRepository.save(Heart(member = member, review = review))
            reviewRepository.save(review)
            HeartResponse(message = "좋아요 성공")
        }
    }
}
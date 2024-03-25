package org.spartaa3.movietogather.domain.review.service

import jakarta.transaction.Transactional
import org.spartaa3.movietogather.domain.comments.repository.CommentsRepository
import org.spartaa3.movietogather.domain.member.repository.MemberRepository
import org.spartaa3.movietogather.domain.review.dto.HeartResponse
import org.spartaa3.movietogather.domain.review.entity.Heart
import org.spartaa3.movietogather.domain.review.repository.HeartRepository
import org.spartaa3.movietogather.domain.review.repository.ReviewRepository
import org.spartaa3.movietogather.infra.security.jwt.UserPrincipal
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class HeartService(
    private val memberRepository: MemberRepository,
    private val reviewRepository: ReviewRepository,
    private val commentRepository: CommentsRepository,
    private val heartRepository: HeartRepository
) {
    @Transactional
    fun reviewHeart(reviewId: Long, userPrincipal: UserPrincipal): HeartResponse {
        val member = memberRepository.findByEmail(userPrincipal.email)
        val review = reviewRepository.findByIdOrNull(reviewId) ?: throw IllegalArgumentException()

        val existingHeart = heartRepository.findByMemberAndReviewAndCommentsIsNull(member, review)

        return if (existingHeart != null) {
            heartRepository.deleteByMemberAndReviewAndCommentsIsNull(member, review)
            reviewRepository.save(review)
            HeartResponse(message = "좋아요 취소")
        } else {
            heartRepository.save(Heart(member = member, review = review, comments = null))
            reviewRepository.save(review)
            HeartResponse(message = "좋아요 성공")
        }
    }

    @Transactional
    fun commentHeart(reviewId: Long, userPrincipal: UserPrincipal, commentsId: Long): HeartResponse {
        val member = memberRepository.findByEmail(userPrincipal.email)
        val review = reviewRepository.findByIdOrNull(reviewId) ?: throw IllegalArgumentException()
        val comments = commentRepository.findByIdOrNull(commentsId) ?: throw IllegalArgumentException()

        val existingHeart = heartRepository.findByMemberAndReviewAndComments(member, review, comments)

        return if (existingHeart != null) {
            heartRepository.deleteByMemberAndReviewAndComments(member, review, comments)
            commentRepository.save(comments)
            HeartResponse(message = "좋아요 취소")
        } else {
            heartRepository.save(Heart(member = member, review = review, comments = comments))
            commentRepository.save(comments)
            HeartResponse(message = "좋아요 성공")
        }
    }
}
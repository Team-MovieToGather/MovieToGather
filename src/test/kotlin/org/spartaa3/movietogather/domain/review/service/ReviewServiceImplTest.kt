package org.spartaa3.movietogather.domain.review.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.spartaa3.movietogather.domain.review.entity.Review
import org.spartaa3.movietogather.domain.review.repository.ReviewRepository
import org.spartaa3.movietogather.global.exception.ReviewNotFoundException
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDateTime


class ReviewServiceImplTest : BehaviorSpec({
    afterContainer {
        clearAllMocks()
    }

    val reviewRepository = mockk<ReviewRepository>()
    val reviewService = ReviewServiceImpl(reviewRepository)

    //getReviewById 테스트 : 값이 있을 때
    given("id가 1인 리뷰가 존재한다면") {
        val id = 1L
        val mockReview = mockReview(id)
        every { reviewRepository.findByIdOrNull(any()) } returns mockReview
        `when`("id가 1인 특정 리뷰를 조회했을 때") {
            val result = reviewService.getReviewById(id)
            then("선택한 리뷰의 장르가 조회된다.") {
                result.genre shouldBe mockReview.genre
            }

        }
    }
    //getReviewById 테스트 : 값이 없을 때
    given("id가 1인 리뷰가 존재하지 않는다면") {
        val id = 1L
        every { reviewRepository.findByIdOrNull(any()) } returns null
        `when`("id가 1인 특정 리뷰를 조회했을 때") {
            then("ReviewNotFoundException이 발생한다.") {
                shouldThrow<ReviewNotFoundException> {
                    reviewService.getReviewById(id)
                }
            }
        }
    }
    //createReview 테스트 : 값이 있을 때
    given("id가 1 리뷰가 존재하지 않는다면") {
        val id = 1L
        every { reviewRepository.findByIdOrNull(any()) } returns null
        `when`("id가 1인 특정 리뷰를 조회했을 때") {
            then("ReviewNotFoundException이 발생한다.") {
                shouldThrow<ReviewNotFoundException> {
                    reviewService.getReviewById(id)
                }
            }
        }
    }
    //createReview 테스트 : 값이 있을 때

    //updateReview 테스트 : 값이 있을 때

    //updateReview 테스트 : 값이 있을 때

    //deleteReview 테스트 : 값이 있을 때

    //deleteReview 테스트 : 값이 있을 때

})

fun mockReview(id: Long): Review {
    val mockReview = mockk<Review>()
    every { mockReview.id } returns id
    every { mockReview.postingTitle } returns "Example Posting Title"
    every { mockReview.genre } returns "Action"
    every { mockReview.star } returns 5.0
    every { mockReview.movieTitle } returns "Example Movie Title"
    every { mockReview.movieImg } returns "Example Movie Img"
    every { mockReview.contents } returns "Example Contents"
    every { mockReview.createdAt } returns LocalDateTime.now()
    return mockReview
}

/*

test("createReview") { }
test("updateReview") { }
test("deleteReview") { }

*/
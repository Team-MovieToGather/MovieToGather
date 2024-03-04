package org.spartaa3.movietogather.domain.review.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.spartaa3.movietogather.domain.review.dto.CreateReviewRequest
import org.spartaa3.movietogather.domain.review.dto.ReviewResponse
import org.spartaa3.movietogather.domain.review.dto.UpdateReviewRequest
import org.spartaa3.movietogather.domain.review.entity.Review
import org.spartaa3.movietogather.domain.review.repository.ReviewRepository
import org.spartaa3.movietogather.global.exception.ReviewNotFoundException
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDateTime

@ExtendWith(MockKExtension::class)
@DataJpaTest
class ReviewServiceImplTest : BehaviorSpec({
    afterContainer {
        clearAllMocks()
    }

    val reviewRepository = mockk<ReviewRepository>()
    val reviewService = spyk(ReviewServiceImpl(reviewRepository))

    //getReviewById 테스트 : 값이 있을 때
    given("id가 1인 리뷰가 존재한다면") {
        val id = 1L
        val review = mockReview(id)
        every { reviewRepository.findByIdOrNull(any()) } returns review
        `when`("id가 1인 특정 리뷰를 조회했을 때") {
            val result = reviewService.getReviewById(id)
            then("선택한 리뷰의 장르가 조회된다.") {
                result.genre shouldBe review.genre
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
    //createReview 테스트 : 로그인되었을 때
    given("리뷰 생성을 위해 로그인했을 때 ") {
        // 로그인 구현 시 작성 가능
        //
        val mockRequest = createMockRequest()
        every { reviewRepository.save(any<Review>()) } answers { firstArg() }
        `when`("review를 생성하면") {
            val result = reviewService.createReview(mockRequest)
            then("리뷰가 생성된다.") {
                verify { reviewRepository.save(any<Review>()) }
                result.postingTitle shouldBe mockRequest.postingTitle
            }
        }
    }

    /*
    //createReview 테스트 : 로그인되지 않았을 때
    given("리뷰를 생성하고 싶은데 로그인되지 않았을 때 ") {
        // 로그인 구현 시 작성 가능
        //
        val id = 1L
        val mockRequest = mockRequest()
        `when`("review를 생성하면") {
            reviewService.createReview(mockRequest)
            then("로그인 시 이용 가능합니다. 예외가 발생한다.") {
                // 리뷰 생성 확인
                shouldThrow<IllegalStateException> {
                    reviewRepository.save(any())
                }
            }
        }
    }
 */

    //updateReview 테스트 :  로그인되었을 때
    given("로그인되었을 때 ") {
        // 로그인 구현 시 작성 가능
        `when`("등록된 리뷰의 postingTitle을 수정하면") {
            every { reviewService.updateReview(any(), any()) } returns ReviewResponse(
                id = 1L,
                postingTitle = "New Posting Title",
                genre = "Action",
                star = 5.0,
                movieTitle = "Example Movie Title",
                movieImg = "Example Movie Img",
                contents = "Example Contents",
                createdAt = LocalDateTime.now()
            )
            val result = reviewService.updateReview(1L, updateMockRequest())
            then("등록된 리뷰의 postingTitle의 값은 Request에 입력된 값으로 변경된다.") {
                result.postingTitle shouldBe "New Posting Title"
            }
        }
        `when`("검색한 리뷰가 존재하지 않는다면") {
            every { reviewService.updateReview(any(), any()) } throws ReviewNotFoundException("Review", 1L)
            then("ReviewNotFoundException이 발생한다.") {
                shouldThrow<ReviewNotFoundException> {
                    reviewService.updateReview(1L, updateMockRequest())
                }
            }
        }
    }
    //updateReview 테스트 : 로그인되지 않았을 때

    //deleteReview 테스트 : 로그인되었을 때
    given("유저가 로그인되었을 때 ") {
        // 로그인 구현 시 작성 가능
        `when`("등록된 리뷰를 삭제하면") {
            every { reviewService.deleteReview(any()) } returns Unit
            val result = reviewService.deleteReview(1L)
            then("리뷰가 삭제된다.") {
                result shouldBe Unit
            }
        }
        `when`("검색한 리뷰가 존재하지 않는다면") {
            every { reviewService.deleteReview(any()) } throws ReviewNotFoundException("Review", 1L)
            then("ReviewNotFoundException이 발생한다.") {
                shouldThrow<ReviewNotFoundException> {
                    reviewService.deleteReview(1L)
                }
            }
        }
    }

    //deleteReview 테스트 :: 로그인되지 않았을 때

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

fun createMockRequest(): CreateReviewRequest {
    return CreateReviewRequest(
        postingTitle = "Example Posting Title",
        star = 5.0,
        movieTitle = "Example Movie Title",
        movieImg = "Example Movie Img",
        contents = "Example Contents",
        genre = "Action"
    )
}

fun updateMockRequest(): UpdateReviewRequest {
    return UpdateReviewRequest(
        postingTitle = "New Posting Title",
        star = 5.0,
        movieTitle = "Example Movie Title",
        movieImg = "Example Movie Img",
        contents = "Example Contents",
        genre = "Action"
    )
}

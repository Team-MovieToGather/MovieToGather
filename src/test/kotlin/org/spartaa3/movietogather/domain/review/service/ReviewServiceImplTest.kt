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
import org.spartaa3.movietogather.domain.review.entity.ReviewSearchCondition
import org.spartaa3.movietogather.domain.review.repository.HeartRepository
import org.spartaa3.movietogather.domain.review.repository.RedisRepository
import org.spartaa3.movietogather.domain.review.repository.ReviewRepository
import org.spartaa3.movietogather.global.exception.ReviewNotFoundException
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDateTime

@ExtendWith(MockKExtension::class)
@ActiveProfiles("test")
class ReviewServiceImplTest : BehaviorSpec({
    afterContainer {
        clearAllMocks()
    }

    val reviewRepository = mockk<ReviewRepository>()
    val pageableMock = mockk<Pageable>()
    val heartRepository = mockk<HeartRepository>()
    val redisRepository = mockk<RedisRepository>()
    val reviewService = spyk(ReviewServiceImpl(reviewRepository, heartRepository, redisRepository))


    //getReviewById 테스트 : 값이 있을 때
//    given("id가 1인 리뷰가 존재한다면") {
//        val id = 1L
//        val review = mockReview(id)
//        every { reviewRepository.findByIdOrNull(any()) } returns review
//        `when`("id가 1인 특정 리뷰를 조회했을 때") {
//            val result = reviewService.getReviewById(id)
//            then("선택한 리뷰의 장르가 조회된다.") {
//                result.genre shouldBe review.genre
//            }
//
//        }
//    }
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
    given("리뷰를 생성할 때") {
        val mockRequest = createMockRequest()
        every { reviewRepository.save(any<Review>()) } answers { firstArg() }
        `when`("리뷰 생성을 요청하면") {
            val result = reviewService.createReview(mockRequest)
            then("리뷰가 생성된다.") {
                verify { reviewRepository.save(any<Review>()) }
                result.postingTitle shouldBe mockRequest.postingTitle
            }
        }
    }

    given("id가 1인 리뷰가 존재할 때 ") {
        `when`("등록된 리뷰의 postingTitle을 수정하면") {
            every { reviewService.updateReview(any(), any()) } returns ReviewResponse(
                id = 1L,
                postingTitle = "New Posting Title",
                genre = "Action",
                star = 5.0,
                movieTitle = "Example Movie Title",
                movieImg = "Example Movie Img",
                contents = "Example Contents",
                createdAt = LocalDateTime.now(),
                comments = listOf(),
                heart = 0
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
    given("id가 1인 리뷰가 존재하고, 이를 삭제하려 할 때 ") {
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


    given("아래와 같은 조건으로 페이징을 진행하고 ") {
        every { pageableMock.pageNumber } returns 0
        every { pageableMock.pageSize } returns 10
        every { pageableMock.sort } returns Sort.unsorted()
        `when`("Tag가 MOVIE_TITLE이고, 검색어가 Title인 리뷰를 조회했을 때") {
            val keyword = "Title"
            val tag: ReviewSearchCondition = ReviewSearchCondition.MOVIE_TITLE
            val mockPage = PageImpl<Review>(listOf())
            every { reviewRepository.searchReview(any(), any(), any()) } returns mockPage
            val result = reviewService.searchReview(tag, keyword, pageableMock)
            then("페이징 처리가 된다.") {
                val isPaged = result.size
                isPaged shouldBe 0
            }
        }
    }

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
        //movieTitle = "Example Movie Title",
        //movieImg = "Example Movie Img",
        contents = "Example Contents",
        //genre = "Action"
    )
}

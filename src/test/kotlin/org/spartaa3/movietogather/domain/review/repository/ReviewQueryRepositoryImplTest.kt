package org.spartaa3.movietogather.domain.review.repository

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.mockk.clearAllMocks
import org.spartaa3.movietogather.domain.review.entity.Review
import org.spartaa3.movietogather.domain.review.entity.ReviewSearchCondition
import org.spartaa3.movietogather.infra.QueryDslSupport
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.data.domain.Pageable
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDateTime

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(value = [QueryDslSupport::class])
@ActiveProfiles("test")
class ReviewRepositoryImplTest @Autowired constructor(
    private val reviewRepository: ReviewRepository
) : BehaviorSpec({

    afterContainer {
        clearAllMocks()
    }

    val defaultReviewList = listOf(
        Review(
            postingTitle = "postingTitle",
            movieTitle = "movieTitle",
            movieImg = "movieImg",
            genre = "genre",
            contents = "contents",
            star = 5.0,
            createdAt = LocalDateTime.now()
        ),
        Review(
            postingTitle = "postingTitle",
            movieTitle = "movie",
            movieImg = "movieImg",
            genre = "genre",
            contents = "contents",
            star = 5.0,
            createdAt = LocalDateTime.now()
        ), Review(
            postingTitle = "posting",
            movieTitle = "movieTitle",
            movieImg = "movieImg",
            genre = "genre",
            contents = "contents",
            star = 5.0,
            createdAt = LocalDateTime.now()
        )

    )
    given("searchReview를 실행할 때") {
        reviewRepository.saveAllAndFlush(defaultReviewList)
        `when`("리뷰 제목 기준으로 검색했을 때 Review 데이터가 있다면") {
            val pageable = Pageable.ofSize(10)
            val result = reviewRepository.searchReview(ReviewSearchCondition.POSTING_TITLE, "Title", pageable)

            then("조건에 맞는 Review 데이터가 출력된다.") {
                result.content.map { it.postingTitle shouldContain "Title" }
                result.content.size shouldBe 2
            }
        }
        `when`("리뷰 제목 기준으로 검색했을 때 조건에 맞는 Review 데이터가 없다면") {
            val pageable = Pageable.ofSize(10)
            val result = reviewRepository.searchReview(ReviewSearchCondition.POSTING_TITLE, "타이틀", pageable)
            then("빈 리스트가 출력된다.")
            {
                result.content.size shouldBe 0
            }
        }
        `when`("영화 제목 기준으로 검색했을 때 Review 데이터가 있다면") {
            val pageable = Pageable.ofSize(10)
            val result = reviewRepository.searchReview(ReviewSearchCondition.MOVIE_TITLE, "Title", pageable)

            then("조건에 맞는 Review 데이터가 출력된다.") {
                result.content.map { it.movieTitle shouldContain "Title" }
                result.content.size shouldBe 2
            }
        }
        `when`("영화 제목 기준으로 검색했을 때 조건에 맞는 Review 데이터가 없다면") {
            val pageable = Pageable.ofSize(10)
            val result = reviewRepository.searchReview(ReviewSearchCondition.MOVIE_TITLE, "타이틀", pageable)
            then("빈 리스트가 출력된다.")
            {
                result.content.size shouldBe 0
            }
        }
    }


})
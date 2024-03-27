package org.spartaa3.movietogather.domain.review.entity

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
class ReviewTest : BehaviorSpec({
    given("Review Entity가 주어졌을 때") {
        val review = Review(
            postingTitle = "postingTitle",
            movieTitle = "movieTitle",
            movieImg = "movieImg",
            genre = "genre",
            contents = "contents",
        )

        `when`("Entity를 Response로 변환하면") {
            val response = review.toResponse()
            then("Entity에 정의된 값과 반환된 Response의 값은 같아야 한다.") {
                response.postingTitle shouldBe review.postingTitle
                response.genre shouldBe review.genre
//                response.star shouldBe review.star
                response.movieTitle shouldBe review.movieTitle
                response.movieImg shouldBe review.movieImg
                response.contents shouldBe review.contents
                response.createdAt shouldBe review.createdAt

            }
        }
    }
})

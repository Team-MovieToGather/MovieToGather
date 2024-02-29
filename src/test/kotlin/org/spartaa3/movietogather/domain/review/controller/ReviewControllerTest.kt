package org.spartaa3.movietogather.domain.review.controller

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.extension.ExtendWith
import org.spartaa3.movietogather.domain.review.dto.ReviewResponse
import org.spartaa3.movietogather.domain.review.service.ReviewService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType

import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.time.LocalDateTime

@WebMvcTest
@AutoConfigureMockMvc
@ExtendWith(MockKExtension::class)
class ReviewControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
) : DescribeSpec({

    afterContainer {
        clearAllMocks()
    }
    val reviewService = mockk<ReviewService>()
    describe("GET /api/reviews/{reviewId} 는") {
        val reviewId = 1L
        every { reviewService.getReviewById(any()) } returns ReviewResponse(
            id = reviewId,
            postingTitle = "postingTitle",
            star = 5.0,
            movieTitle = "movieTitle",
            movieImg = "movieImg",
            contents = "contents",
            genre = "genre",
            createdAt = LocalDateTime.now()
        )
        context("존재하는 Id에 대한 요청을 보낼 때") {
            val result= mockMvc.perform(
                MockMvcRequestBuilders.get("/api/reviews/$reviewId")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            ).andReturn()
            it("200 status code를 응답해야 한다.") {
                result.response.status shouldBe 200
            }
        }
    }
})

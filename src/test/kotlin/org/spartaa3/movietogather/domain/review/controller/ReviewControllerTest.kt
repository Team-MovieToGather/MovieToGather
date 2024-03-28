package org.spartaa3.movietogather.domain.review.controller

import io.kotest.core.spec.style.DescribeSpec
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.extension.ExtendWith
import org.spartaa3.movietogather.MovieToGatherApplicationTests
import org.spartaa3.movietogather.domain.member.entity.Member
import org.spartaa3.movietogather.domain.member.entity.MemberRole
import org.spartaa3.movietogather.domain.member.repository.MemberRepository
import org.spartaa3.movietogather.domain.review.dto.CreateReviewRequest
import org.spartaa3.movietogather.domain.review.dto.ReviewResponse
import org.spartaa3.movietogather.domain.review.dto.UpdateReviewRequest
import org.spartaa3.movietogather.domain.review.service.ReviewService
import org.spartaa3.movietogather.global.exception.ReviewNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Page
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDateTime

@SpringBootTest(classes = [MovieToGatherApplicationTests::class])
@AutoConfigureMockMvc
@ExtendWith(MockKExtension::class)
@ActiveProfiles("test")
class ReviewControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
) : DescribeSpec({

    afterContainer {
        clearAllMocks()
    }
    val reviewService = mockk<ReviewService>()
    val memberRepository = spyk<MemberRepository>()

    val reviewId = 1L
    val member = Member(
        email = "abb@gmail.com",
        nickname = "abb",
        role = MemberRole.MEMBER,
//        providerId = ""
    )
    val email = "abb@gmail.com"

    // getReviewById 테스트
    describe("GET /api/reviews/{reviewId} 는") {


        context("존재하는 Id에 대한 요청을 보낼 때") {
            every { reviewService.getReviewById(any()) } returns ReviewResponse(
                id = reviewId,
                postingTitle = "postingTitle",
                movieTitle = "movieTitle",
                movieImg = "movieImg",
                contents = "contents",
                genre = "genre",
                createdAt = LocalDateTime.now(),
                comments = listOf(),
                heart = 0
            )
            it("200 status code를 응답해야 한다.") {
                mockMvc.get("/api/reviews/$reviewId") {
                    contentType = MediaType.APPLICATION_JSON
                    accept = MediaType.APPLICATION_JSON
                }.andExpect {
                    status {
                        MockMvcResultMatchers.status().isOk()
                    }
                }
            }
        }
        context("존재하지 않는 Id에 대한 요청을 보낼 때") {

            every { reviewService.deleteReview(email, reviewId) } throws ReviewNotFoundException("Review", reviewId)
            it("404 status code를 응답해야 한다.") {
                mockMvc.get("/api/reviews/$reviewId") {
                    contentType = MediaType.APPLICATION_JSON
                    accept = MediaType.APPLICATION_JSON
                }.andExpect {
                    status {
                        MockMvcResultMatchers.status().isNotFound()
                    }
                }
            }
        }
    }
    // createReview 테스트
    describe("POST /api/reviews 는") {
        val reviewId = 1L
        val request = CreateReviewRequest(
            postingTitle = "postingTitle",
            contents = "contents",
            movieTitle = "movieTitle",
            movieImg = "movieImg",
            genre = "contents",
            )
        context("존재하는 Id에 대한 요청을 보낼 때") {
            every { reviewService.createReview(email, request) } returns ReviewResponse(
                id = reviewId,
                postingTitle = request.postingTitle,
                movieTitle = "movieTitle",
                movieImg = "movieImg",
                contents = request.contents,
                genre = "contents",
                createdAt = LocalDateTime.now(),
                comments = listOf(),
                heart = 0
            )
            it("200 status code를 응답해야 한다.") {
                mockMvc.post("/api/reviews") {
                    contentType = MediaType.APPLICATION_JSON
                    accept = MediaType.APPLICATION_JSON
                }.andExpect {
                    status {
                        MockMvcResultMatchers.status().isCreated()
                    }
                }
            }
        }
        context("존재하지 않는 Id에 대한 요청을 보낼 때") {
            every { reviewService.deleteReview(email, reviewId) } throws ReviewNotFoundException("Review", reviewId)
            it("404 status code를 응답해야 한다.") {
                mockMvc.post("/api/reviews") {
                    contentType = MediaType.APPLICATION_JSON
                    accept = MediaType.APPLICATION_JSON
                }.andExpect {
                    status {
                        MockMvcResultMatchers.status().isCreated()
                    }
                }
            }
        }
    }
    // updateReview 테스트
    describe("PUT /api/reviews 는") {
        val reviewId = 1L
        val request = UpdateReviewRequest(
            postingTitle = "newTitle",
            //movieTitle = "newTitle",
            //movieImg = "newImg",
            contents = "newContents",
            //genre = "newGenre"
        )
        context("존재하는 Id에 대한 요청을 보낼 때") {
            every { reviewService.updateReview(email, reviewId, request) } returns ReviewResponse(
                id = reviewId,
                postingTitle = request.postingTitle,
                movieTitle = "movieTitle",
                movieImg = "movieImg",
                contents = request.contents,
                genre = "contents",
                createdAt = LocalDateTime.now(),
                comments = listOf(),
                heart = 0
            )
        }
        it("200 status code를 응답해야 한다.") {
            mockMvc.put("/api/reviews$reviewId") {
                contentType = MediaType.APPLICATION_JSON
                accept = MediaType.APPLICATION_JSON
            }.andExpect {
                status {
                    MockMvcResultMatchers.status().isOk()
                }
            }
        }
        context("존재하지 않는 Id에 대한 요청을 보낼 때") {
            every { reviewService.deleteReview(email, reviewId) } throws ReviewNotFoundException("Review", reviewId)
            it("404 Status Code를 응답해야 한다..") {
                mockMvc.put("/api/reviews$reviewId") {
                    contentType = MediaType.APPLICATION_JSON
                    accept = MediaType.APPLICATION_JSON
                }.andExpect {
                    status {
                        MockMvcResultMatchers.status().isNotFound()
                    }
                }
            }
        }
    }


    // deleteReview 테스트
    describe("Delete /api/reviews 는") {
        val reviewId = 1L
        context("존재하는 Id에 대한 요청을 보낼 때") {
            every { reviewService.deleteReview(email, reviewId) } returns Unit
            it("204 status code를 응답해야 한다.") {
                mockMvc.delete("/api/reviews/$reviewId") {
                    contentType = MediaType.APPLICATION_JSON
                    accept = MediaType.APPLICATION_JSON
                }.andExpect {
                    status {
                        MockMvcResultMatchers.status().isNoContent()
                    }
                }
            }
        }
        context("존재하지 않는 Id에 대한 요청을 보낼 때") {
            every { reviewService.deleteReview(email, reviewId) } throws ReviewNotFoundException("Review", reviewId)
            it("404 status code를 응답해야 한다.") {
                mockMvc.delete("/api/reviews/$reviewId") {
                    contentType = MediaType.APPLICATION_JSON
                    accept = MediaType.APPLICATION_JSON
                }.andExpect {
                    status {
                        MockMvcResultMatchers.status().isNoContent()
                    }
                }
            }
        }
    }
    describe("GET /api/reviews/search 는") {

        context("검색한 조건의 데이터가 존재한다면") {
            val condition = "postingTitle"
            val keyword = "keyword"
            every { reviewService.searchReview(any(), any(), any()) } returns Page.empty()
            it("200 status code를 응답해야 한다.") {
                mockMvc.get("/api/reviews/search") {
                    contentType = MediaType.APPLICATION_JSON
                    accept = MediaType.APPLICATION_JSON
                    param("searchCondition", condition)
                    param("keyword", keyword)
                }.andExpect {
                    status {
                        MockMvcResultMatchers.status().isOk()
                    }
                }
            }
        }


    }
})


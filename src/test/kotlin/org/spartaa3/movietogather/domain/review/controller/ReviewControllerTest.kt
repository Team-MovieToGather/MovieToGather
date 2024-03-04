//package org.spartaa3.movietogather.domain.review.controller
//
//import io.kotest.core.spec.style.DescribeSpec
//import io.mockk.clearAllMocks
//import io.mockk.every
//import io.mockk.junit5.MockKExtension
//import io.mockk.mockk
//import org.junit.jupiter.api.extension.ExtendWith
//import org.spartaa3.movietogather.domain.review.dto.CreateReviewRequest
//import org.spartaa3.movietogather.domain.review.dto.ReviewResponse
//import org.spartaa3.movietogather.domain.review.dto.UpdateReviewRequest
//import org.spartaa3.movietogather.domain.review.service.ReviewService
//import org.spartaa3.movietogather.global.exception.ReviewNotFoundException
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.http.MediaType
//import org.springframework.test.web.servlet.*
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers
//import java.time.LocalDateTime
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@ExtendWith(MockKExtension::class)
//class ReviewControllerTest @Autowired constructor(
//    private val mockMvc: MockMvc,
//) : DescribeSpec({
//
//    afterContainer {
//        clearAllMocks()
//    }
//    val reviewService = mockk<ReviewService>()
//    // getReviewById 테스트
//    describe("GET /api/reviews/{reviewId} 는") {
//        val reviewId = 1L
//        context("존재하는 Id에 대한 요청을 보낼 때") {
//            every { reviewService.getReviewById(any()) } returns ReviewResponse(
//                id = reviewId,
//                postingTitle = "postingTitle",
//                star = 5.0,
//                movieTitle = "movieTitle",
//                movieImg = "movieImg",
//                contents = "contents",
//                genre = "genre",
//                createdAt = LocalDateTime.now()
//            )
//            it("200 status code를 응답해야 한다.") {
//                mockMvc.get("/api/reviews/$reviewId") {
//                    contentType = MediaType.APPLICATION_JSON
//                    accept = MediaType.APPLICATION_JSON
//                }.andExpect {
//                    status {
//                        MockMvcResultMatchers.status().isOk()
//                    }
//                }
//            }
//        }
//        context("존재하지 않는 Id에 대한 요청을 보낼 때") {
//            every { reviewService.deleteReview(reviewId) } throws ReviewNotFoundException("Review", reviewId)
//            it("404 status code를 응답해야 한다.") {
//                mockMvc.get("/api/reviews/$reviewId") {
//                    contentType = MediaType.APPLICATION_JSON
//                    accept = MediaType.APPLICATION_JSON
//                }.andExpect {
//                    status {
//                        MockMvcResultMatchers.status().isNotFound()
//                    }
//                }
//            }
//        }
//    }
//    // createReview 테스트
//    describe("POST /api/reviews 는") {
//        val reviewId = 1L
//        val request = CreateReviewRequest(
//            postingTitle = "postingTitle",
//            star = 5.0,
//            movieTitle = "movieTitle",
//            movieImg = "movieImg",
//            contents = "contents",
//            genre = "genre"
//        )
//        context("존재하는 Id에 대한 요청을 보낼 때") {
//            every { reviewService.createReview(request) } returns ReviewResponse(
//                id = reviewId,
//                postingTitle = request.postingTitle,
//                star = request.star,
//                movieTitle = request.movieTitle,
//                movieImg = request.movieImg,
//                contents = request.contents,
//                genre = request.genre,
//                createdAt = LocalDateTime.now()
//            )
//            it("200 status code를 응답해야 한다.") {
//                mockMvc.post("/api/reviews") {
//                    contentType = MediaType.APPLICATION_JSON
//                    accept = MediaType.APPLICATION_JSON
//                }.andExpect {
//                    status {
//                        MockMvcResultMatchers.status().isCreated()
//                    }
//                }
//            }
//        }
//        context("존재하지 않는 Id에 대한 요청을 보낼 때") {
//            every { reviewService.deleteReview(reviewId) } throws ReviewNotFoundException("Review", reviewId)
//            it("404 status code를 응답해야 한다.") {
//                mockMvc.post("/api/reviews") {
//                    contentType = MediaType.APPLICATION_JSON
//                    accept = MediaType.APPLICATION_JSON
//                }.andExpect {
//                    status {
//                        MockMvcResultMatchers.status().isCreated()
//                    }
//                }
//            }
//        }
//    }
//    // updateReview 테스트
//    describe("PUT /api/reviews 는") {
//        val reviewId = 1L
//        val request = UpdateReviewRequest(
//            postingTitle = "newTitle",
//            star = 5.0,
//            movieTitle = "newTitle",
//            movieImg = "newImg",
//            contents = "newContents",
//            genre = "newGenre"
//        )
//        context("존재하는 Id에 대한 요청을 보낼 때") {
//            every { reviewService.updateReview(reviewId, request) } returns ReviewResponse(
//                id = reviewId,
//                postingTitle = request.postingTitle,
//                star = request.star,
//                movieTitle = request.movieTitle,
//                movieImg = request.movieImg,
//                contents = request.contents,
//                genre = request.genre,
//                createdAt = LocalDateTime.now()
//            )
//        }
//        it("200 status code를 응답해야 한다.") {
//            mockMvc.put("/api/reviews$reviewId") {
//                contentType = MediaType.APPLICATION_JSON
//                accept = MediaType.APPLICATION_JSON
//            }.andExpect {
//                status {
//                    MockMvcResultMatchers.status().isOk()
//                }
//            }
//        }
//        context("존재하지 않는 Id에 대한 요청을 보낼 때") {
//            every { reviewService.deleteReview(reviewId) } throws ReviewNotFoundException("Review", reviewId)
//            it("404 Status Code를 응답해야 한다..") {
//                mockMvc.put("/api/reviews$reviewId") {
//                    contentType = MediaType.APPLICATION_JSON
//                    accept = MediaType.APPLICATION_JSON
//                }.andExpect {
//                    status {
//                        MockMvcResultMatchers.status().isNotFound()
//                    }
//                }
//            }
//        }
//    }
//
//
//    // deleteReview 테스트
//    describe("Delete /api/reviews 는") {
//        val reviewId = 1L
//        context("존재하는 Id에 대한 요청을 보낼 때") {
//            every { reviewService.deleteReview(reviewId) } returns Unit
//            it("204 status code를 응답해야 한다.") {
//                mockMvc.delete("/api/reviews/$reviewId") {
//                    contentType = MediaType.APPLICATION_JSON
//                    accept = MediaType.APPLICATION_JSON
//                }.andExpect {
//                    status {
//                        MockMvcResultMatchers.status().isNoContent()
//                    }
//                }
//            }
//        }
//        context("존재하지 않는 Id에 대한 요청을 보낼 때") {
//            every { reviewService.deleteReview(reviewId) } throws ReviewNotFoundException("Review", reviewId)
//            it("404 status code를 응답해야 한다.") {
//                mockMvc.delete("/api/reviews/$reviewId") {
//                    contentType = MediaType.APPLICATION_JSON
//                    accept = MediaType.APPLICATION_JSON
//                }.andExpect {
//                    status {
//                        MockMvcResultMatchers.status().isNoContent()
//                    }
//                }
//            }
//        }
//    }
//})

package org.spartaa3.movietogather.domain.meetings.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.extension.ExtendWith
import org.spartaa3.movietogather.domain.meetings.dto.meetingsRequest.CreateMeetingsRequest
import org.spartaa3.movietogather.domain.meetings.dto.meetingsRequest.UpdateMeetingsRequest
import org.spartaa3.movietogather.domain.meetings.dto.meetingsResponse.MeetingsResponse
import org.spartaa3.movietogather.domain.meetings.entity.Meetings
import org.spartaa3.movietogather.domain.meetings.repository.MeetingsRepository
import org.spartaa3.movietogather.global.exception.ModelNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDateTime

@ExtendWith(MockKExtension::class)
@ActiveProfiles("test")
class MeetingsServiceImplTest : BehaviorSpec({
    afterContainer {
        clearAllMocks()
    }
    val meetingsRepository = mockk<MeetingsRepository>()
    val meetingsService = spyk(MeetingsServiceImpl(meetingsRepository))


    given("모임 하나를 조회할 때") {
        val id = 1L
        val meetings = mockMeetings(id)

        `when`("해당 모임의 id가 존재하면") {
            every { meetingsRepository.findByIdOrNull(any()) } returns meetings
            val result = meetingsService.getMeetingsById(id)
            then("선택한 모임의 모임 이름을 확인할 수 있다.") {
                result.meetingName shouldBe meetings.meetingName
            }
        }
        `when`("해당 모임의 id가 존재하지 않는다면") {
            every { meetingsRepository.findByIdOrNull(any()) } returns null
            then("MeetingsNotFoundException이 발생한다.") {
                shouldThrow<ModelNotFoundException> {
                    meetingsService.getMeetingsById(id)
                }
            }
        }
    }
    given("모임 하나를 생성할 때") {
        val meetings = mockMeetings(1L)
        every { meetingsRepository.save(any<Meetings>()) } returns meetings
        `when`("모임을 생성을 요청하면") {
            val request = createMockRequest()
            val result = meetingsService.createMeetings(request)
            then("생성된 모임의 모임 제목을 확인할 수 있다.") {
                result.meetingName shouldBe meetings.meetingName
            }

        }

    }
    given("모임 하나를 수정할 때") {
        `when`("요청한 모임이 존재한다면") {
            every { meetingsService.updateMeetings(any(), any()) } returns MeetingsResponse(
                meetingName = "Example Meeting Title1",
                movieName = "Example Movie Title",
                startTime = LocalDateTime.now(),
                endTime = LocalDateTime.now(),
                type = Type.ONLINE,
                locationUrl = "Example Contents",
                isClosed = false,
                numApplicants = 1,
                maxApplicants = 30

            )
            val request = updateMockRequest()
            val result = meetingsService.updateMeetings(1L, request)
            then("수정된 모임의 모임 제목을 확인할 수 있다.") {
                result.meetingName shouldBe "Example Meeting Title1"
            }
        }
        `when`("요청한 모임이 존재하지 않는다면") {
            every { meetingsRepository.findByIdOrNull(any()) } returns null
            then("MeetingsNotFoundException이 발생한다.") {
                shouldThrow<ModelNotFoundException> {
                    meetingsService.updateMeetings(1L, updateMockRequest())
                }
            }
        }
    }
    given("모임 하나를 삭제할 때") {
        `when`("요청한 모임이 존재한다면") {
            every { meetingsRepository.findByIdOrNull(any()) } returns mockMeetings(1L)
            every { meetingsRepository.delete(any()) } returns Unit
            then("모임이 삭제된다.") {
                meetingsService.deleteMeetings(1L) shouldBe Unit
            }
        }
        `when`("요청한 모임이 존재하지 않는다면") {
            every { meetingsRepository.findByIdOrNull(any()) } returns null
            then("MeetingsNotFoundException이 발생한다.") {
                shouldThrow<ModelNotFoundException> {
                    meetingsService.deleteMeetings(1L)
                }
            }
        }
    }

})

fun mockMeetings(id: Long): Meetings {
    val mockMeetings = mockk<Meetings>()
    every { mockMeetings.id } returns id
    every { mockMeetings.meetingName } returns "Example Meeting Title"
    every { mockMeetings.movieName } returns "Example Movie Title"
    every { mockMeetings.startTime } returns LocalDateTime.now()
    every { mockMeetings.endTime } returns LocalDateTime.now()
    every { mockMeetings.type } returns Type.ONLINE
    every { mockMeetings.locationUrl } returns "Example Contents"
    every { mockMeetings.isClosed } returns false
    every { mockMeetings.numApplicants } returns 1
    every { mockMeetings.maxApplicants } returns 30
    return mockMeetings
}

fun createMockRequest(): CreateMeetingsRequest {
    return CreateMeetingsRequest(
        meetingName = "Example Meeting Title",
        movieName = "Example Movie Title",
        startTime = LocalDateTime.now(),
        endTime = LocalDateTime.now(),
        type = Type.ONLINE,
        locationUrl = "Example Contents",
        isClosed = false,
        numApplicants = 1,
        maxApplicants = 30

    )
}

fun updateMockRequest(): UpdateMeetingsRequest {
    return UpdateMeetingsRequest(
        meetingName = "Example Meeting Title1",
        movieName = "Example Movie Title",
        startTime = LocalDateTime.now(),
        endTime = LocalDateTime.now(),

        )
}
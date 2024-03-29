package org.spartaa3.movietogather.domain.meetings.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.redisson.api.RLock
import org.redisson.api.RedissonClient
import org.spartaa3.movietogather.domain.meetings.dto.meetingsRequest.CreateMeetingsRequest
import org.spartaa3.movietogather.domain.meetings.dto.meetingsRequest.UpdateMeetingsRequest
import org.spartaa3.movietogather.domain.meetings.dto.meetingsResponse.MeetingsResponse
import org.spartaa3.movietogather.domain.meetings.entity.MeetingMember
import org.spartaa3.movietogather.domain.meetings.entity.Meetings
import org.spartaa3.movietogather.domain.meetings.repository.MeetingMemberRepository
import org.spartaa3.movietogather.domain.meetings.repository.MeetingsRepository
import org.spartaa3.movietogather.domain.member.entity.Member
import org.spartaa3.movietogather.domain.member.entity.MemberRole
import org.spartaa3.movietogather.domain.member.repository.MemberRepository
import org.spartaa3.movietogather.global.exception.ModelNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.util.ReflectionTestUtils
import java.time.LocalDateTime
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@ExtendWith(MockKExtension::class)
@ActiveProfiles("test")
class MeetingsServiceImplTest : BehaviorSpec({
    afterContainer {
        clearAllMocks()
    }
    val meetingsRepository = mockk<MeetingsRepository>()
    val memberRepository = spyk<MemberRepository>()
    val meetingMemberRepository = spyk<MeetingMemberRepository>()
    val redissonClient = mockk<RedissonClient>()
    val lock = mockk<RLock>()
    val meetingsService =
        spyk(MeetingsServiceImpl(meetingsRepository, memberRepository, meetingMemberRepository, redissonClient))



    given("모임 하나를 조회할 때") {
        val id = 1L
        val meetings = mockMeetings()
        ReflectionTestUtils.setField(meetings, "id", 1L)
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
        val meetings = mockMeetings()
        ReflectionTestUtils.setField(meetings, "id", 1L)
        val member = Member(
            email = "abb@gmail.com",
            nickname = "abb",
            role = MemberRole.MEMBER,
            providerId= "asddgsd"
        )
        val email = "abb@gmail.com"
        every { memberRepository.findByEmail(any()) } returns member
        every { meetingsRepository.save(any()) } returns meetings
        `when`("모임을 생성을 요청하면") {
            val request = createMockRequest()
            every { meetingMemberRepository.save(MeetingMember(meetings, member)) } returns MeetingMember(
                meetings,
                member
            )
            val result = meetingsService.createMeetings(email, request)
            then("생성된 모임의 모임 제목을 확인할 수 있다.") {
                result.meetingName shouldBe meetings.meetingName
            }

        }

    }
    given("모임 하나를 수정할 때") {
        `when`("요청한 모임이 존재한다면") {
            every { meetingsService.updateMeetings(any(), any()) } returns MeetingsResponse(
                id = 1L,
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
            every { meetingsRepository.findByIdOrNull(any()) } returns mockMeetings()
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

    given("모임에 30개의 자리가 있을 때") {
        val meetings = Meetings(
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
        every { meetingsRepository.findByIdOrNull(any()) } returns meetings
        `when`("30명이 동시에 모임에 신청하면") {
            val numberOfThreads = 30
            val service = Executors.newFixedThreadPool(30)
            for (i in 1..numberOfThreads) {
                val member = Member(
                    email = "abb${i}@gmail.com",
                    nickname = "abb${i}",
                    role = MemberRole.MEMBER,
                    providerId = "sadafa"
                )
                val meetingMember = MeetingMember(
                    meetings,
                    member
                )
                ReflectionTestUtils.setField(member, "id", i.toLong())
                every { memberRepository.findByEmail("abb${i}@gmail.com") } returns member
                every { meetingMemberRepository.existsByMeetingsAndMember(meetings, member) } returns false
                every { meetingMemberRepository.save(meetingMember) } returns meetingMember
                service.execute(Runnable {
                    every { lock.lock() } just Runs
                    every { lock.unlock() } just Runs
                    every { redissonClient.getLock("meeting:1") } returns lock
                    every { lock.tryLock(any<Long>(), any<Long>(), eq(TimeUnit.SECONDS)) } returns true
                    meetingsService.joinMeetings("abb${i}@gmail.com", 1L)
                })
            }
            service.shutdown()
            service.awaitTermination(1, TimeUnit.MINUTES)
            then("남은 모임의 자리수는 0이다.") {
                meetings.numApplicants shouldBe 30

            }
        }
    }
})

fun mockMeetings(): Meetings {
    val mockMeetings = Meetings(
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
        startTime = LocalDateTime.now(),
        endTime = LocalDateTime.now(),

        )
}
package org.spartaa3.movietogather.domain.meetings.service


import jakarta.transaction.Transactional
import org.redisson.api.RedissonClient
import org.spartaa3.movietogather.domain.meetings.dto.meetingsRequest.CreateMeetingsRequest
import org.spartaa3.movietogather.domain.meetings.dto.meetingsRequest.UpdateMeetingsRequest
import org.spartaa3.movietogather.domain.meetings.dto.meetingsResponse.MeetingsResponse
import org.spartaa3.movietogather.domain.meetings.entity.MeetingMember
import org.spartaa3.movietogather.domain.meetings.entity.MeetingSearchCondition
import org.spartaa3.movietogather.domain.meetings.entity.Meetings
import org.spartaa3.movietogather.domain.meetings.entity.toResponse
import org.spartaa3.movietogather.domain.meetings.repository.MeetingMemberRepository
import org.spartaa3.movietogather.domain.meetings.repository.MeetingsRepository
import org.spartaa3.movietogather.domain.member.entity.Member
import org.spartaa3.movietogather.domain.member.repository.MemberRepository
import org.spartaa3.movietogather.global.exception.ModelNotFoundException
import org.spartaa3.movietogather.infra.security.jwt.UserPrincipal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

enum class Type {
    ONLINE,
    OFFLINE,
    ALL
}

@Service
class MeetingsServiceImpl(
    private val meetingsRepository: MeetingsRepository,
    private val memberRepository: MemberRepository,
    private val meetingMemberRepository: MeetingMemberRepository,
    @Autowired val redissonClient: RedissonClient
) : MeetingsService {
    override fun searchMeeting(
        type: Type,
        condition: MeetingSearchCondition,
        keyword: String?,
        pageable: Pageable
    ): Page<MeetingsResponse> {
        val meetings = meetingsRepository.searchMeeting(type, condition, keyword, pageable)
        return meetings.map { it.toResponse() }
    }

    override fun getMeetingsById(meetingId: Long): MeetingsResponse {
        val meetings =
            meetingsRepository.findByIdOrNull(meetingId) ?: throw ModelNotFoundException("meeting", meetingId)
        return meetings.toResponse()
    }

    @Transactional
    override fun createMeetings(email: String, request: CreateMeetingsRequest): MeetingsResponse {
        val member = memberRepository.findByEmail(email)
        val meeting = meetingsRepository.save(
            Meetings(
                meetingName = request.meetingName,
                movieName = request.movieName,
                startTime = request.startTime,
                endTime = request.endTime,
                type = request.type,
                locationUrl = request.locationUrl,
                isClosed = request.isClosed,
                numApplicants = request.numApplicants,
                maxApplicants = request.maxApplicants,
            )
        )
        // 모임과 회원의 관계를 저장
        meetingMemberRepository.save(
            MeetingMember(
                meeting,
                member
            )
        )

        return meeting.toResponse()
    }

    @Transactional
    override fun updateMeetings(meetingId: Long, request: UpdateMeetingsRequest): MeetingsResponse {
        val meetings =
            meetingsRepository.findByIdOrNull(meetingId) ?: throw ModelNotFoundException("Meetings", meetingId)
        val (meetingName, startTime, endTime) = request

        meetings.meetingName = meetingName
        meetings.startTime = startTime
        meetings.endTime = endTime

        return meetingsRepository.save(meetings).toResponse()

    }

    @Transactional
    override fun deleteMeetings(meetingId: Long) {
        val meetings =
            meetingsRepository.findByIdOrNull(meetingId) ?: throw ModelNotFoundException("Meetings", meetingId)
        meetingsRepository.delete(meetings)
    }

    @Transactional
    override fun joinMeetings(email: String, meetingId: Long) {
        val meetings =
            meetingsRepository.findByIdOrNull(meetingId) ?: throw ModelNotFoundException("Meetings", meetingId)
        val member = memberRepository.findByEmail(email)
        val lock = redissonClient.getLock("meeting:$meetingId")
        if (meetingMemberRepository.existsByMeetingsAndMember(meetings, member)) {
            throw IllegalStateException("이미 참가한 모임입니다.")
        } else {
            if (meetings.numApplicants >= meetings.maxApplicants) {
                throw IllegalStateException("모임 인원이 꽉 찼습니다.")
            } else {
                // 락 획득시간 & 락 만료시간
                if (lock.tryLock(2, 3, TimeUnit.SECONDS)) {
                    try {
                        meetings.numApplicants += 1
                        meetingMemberRepository.save(MeetingMember(meetings, member))
                        // 모임이 꽉 찼을 경우 isClosed를 true로 변경
                        meetings.isClosed()
                    } finally {
                        lock.unlock()
                    }
                }
            }
        }
    }

    override fun getMyMeetings(email: String, meetingId: Long): List<MeetingsResponse> {
        TODO("Not yet implemented")
    }
}
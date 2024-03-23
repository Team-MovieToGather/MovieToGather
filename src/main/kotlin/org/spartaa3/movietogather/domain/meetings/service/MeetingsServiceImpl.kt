package org.spartaa3.movietogather.domain.meetings.service


import org.spartaa3.movietogather.domain.meetings.dto.meetingsRequest.CreateMeetingsRequest
import org.spartaa3.movietogather.domain.meetings.dto.meetingsRequest.UpdateMeetingsRequest
import org.spartaa3.movietogather.domain.meetings.dto.meetingsResponse.MeetingsResponse
import org.spartaa3.movietogather.domain.meetings.entity.MeetingSearchCondition
import org.spartaa3.movietogather.domain.meetings.entity.Meetings
import org.spartaa3.movietogather.domain.meetings.entity.toResponse
import org.spartaa3.movietogather.domain.meetings.repository.MeetingsRepository
import org.spartaa3.movietogather.global.exception.ModelNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

enum class Type {
    ONLINE,
    OFFLINE,
    ALL
}

@Service
class MeetingsServiceImpl(
    private val meetingsRepository: MeetingsRepository
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

    override fun createMeetings(request: CreateMeetingsRequest): MeetingsResponse {
        return meetingsRepository.save(
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
        ).toResponse()
    }

    override fun updateMeetings(meetingId: Long, request: UpdateMeetingsRequest): MeetingsResponse {
        val meetings =
            meetingsRepository.findByIdOrNull(meetingId) ?: throw ModelNotFoundException("Meetings", meetingId)
        val (meetingName, startTime, endTime) = request

        meetings.meetingName = meetingName
        meetings.startTime = startTime
        meetings.endTime = endTime

        return meetingsRepository.save(meetings).toResponse()

    }

    override fun deleteMeetings(meetingId: Long) {
        val meetings =
            meetingsRepository.findByIdOrNull(meetingId) ?: throw ModelNotFoundException("Meetings", meetingId)
        meetingsRepository.delete(meetings)
    }
}
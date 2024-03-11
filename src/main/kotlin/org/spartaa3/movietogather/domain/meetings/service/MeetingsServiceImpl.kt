package org.spartaa3.movietogather.domain.meetings.service


import org.spartaa3.movietogather.domain.meetings.dto.meetingsRequest.CreateMeetingsRequest
import org.spartaa3.movietogather.domain.meetings.dto.meetingsRequest.UpdateMeetingsRequest
import org.spartaa3.movietogather.domain.meetings.dto.meetingsResponse.MeetingsResponse
import org.spartaa3.movietogather.domain.meetings.entity.Meetings
import org.spartaa3.movietogather.domain.meetings.entity.toResponse
import org.spartaa3.movietogather.domain.meetings.globl.exception.MeetingsNotFoundException
import org.spartaa3.movietogather.domain.meetings.repository.MeetingsRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

enum class Type {
    ONLINE,
    OFFLINE,
}

@Service
class MeetingsServiceImpl(
    private val meetingsRepository: MeetingsRepository
) : MeetingsService {

    override fun getMeetingsById(meetingsId: Long): MeetingsResponse {
        val meetings =
            meetingsRepository.findByIdOrNull(meetingsId) ?: throw MeetingsNotFoundException("meetings", meetingsId)
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

    override fun updateMeetings(meetingsId: Long, request: UpdateMeetingsRequest): MeetingsResponse {
        val meetings = meetingsRepository.findByIdOrNull(meetingsId) ?: throw MeetingsNotFoundException(
            "Meetings",
            meetingsId
        )
        val (meetingName, movieName, startTime, endTime) = request

        meetings.meetingName = meetingName
        meetings.movieName = movieName
        meetings.startTime = startTime
        meetings.endTime = endTime

        return meetingsRepository.save(meetings).toResponse()

    }

    override fun deleteMeetings(meetingsId: Long) {
        val meetings =
            meetingsRepository.findByIdOrNull(meetingsId) ?: throw MeetingsNotFoundException("Meetings", meetingsId)
        meetingsRepository.delete(meetings)
    }
}
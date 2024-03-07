package org.spartaa3.movietogather.domain.meetings.service


import org.spartaa3.movietogather.domain.meetings.dto.mettingsRequest.CreateMeetingsRequest
import org.spartaa3.movietogather.domain.meetings.dto.mettingsRequest.UpdateMeetingsRequest
import org.spartaa3.movietogather.domain.meetings.entity.meetings
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
    override fun createMeetings(meetingsId: String, request: CreateMeetingsRequest) {
        meetingsRepository.save(
            meetings(
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
    }

    override fun updateMeetings(meetingsId: String, request: UpdateMeetingsRequest) {
        val meetings = meetingsRepository.findByIdOrNull(meetingsId) ?: throw MeetingsNotFoundException(
            "Meetings",
            meetingsId
        ) //리뷰가아님
        val (meetingName, movieName, startTime, endTime) = request

        meetings.meetingName = meetingName
        meetings.movieName = movieName
        meetings.startTime = startTime
        meetings.endTime = endTime

        meetingsRepository.save(meetings)

    }

    override fun deleteMeetings(meetingsId: String) {
        val meetings =
            meetingsRepository.findByIdOrNull(meetingsId) ?: throw MeetingsNotFoundException("Meetings", meetingsId)
        meetingsRepository.delete(meetings)
    }
}
package org.spartaa3.movietogather.domain.meetings.service

import org.spartaa3.movietogather.domain.meetings.dto.meetingsRequest.CreateMeetingsRequest
import org.spartaa3.movietogather.domain.meetings.dto.meetingsRequest.UpdateMeetingsRequest
import org.spartaa3.movietogather.domain.meetings.dto.meetingsResponse.MeetingsResponse
import org.spartaa3.movietogather.domain.meetings.entity.MeetingSearchCondition
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface MeetingsService {

    fun searchMeeting(
        type: Type,
        condition: MeetingSearchCondition,
        keyword: String?,
        pageable: Pageable
    ): Page<MeetingsResponse>

    fun getMeetingsById(meetingId: Long): MeetingsResponse

    fun createMeetings(email: String, request: CreateMeetingsRequest): MeetingsResponse

    fun updateMeetings(meetingId: Long, request: UpdateMeetingsRequest): MeetingsResponse

    fun deleteMeetings(meetingId: Long)
    fun joinMeetings(email: String, meetingId: Long)
    fun getMyMeetings(email: String, meetingId: Long): List<MeetingsResponse>
}
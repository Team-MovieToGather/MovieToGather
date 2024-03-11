package org.spartaa3.movietogather.domain.meetings.service

import org.spartaa3.movietogather.domain.meetings.dto.meetingsRequest.CreateMeetingsRequest
import org.spartaa3.movietogather.domain.meetings.dto.meetingsRequest.UpdateMeetingsRequest
import org.spartaa3.movietogather.domain.meetings.dto.meetingsResponse.MeetingsResponse
import org.spartaa3.movietogather.domain.meetings.entity.MeetingSearchCondition
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface MeetingsService {

    fun searchMeeting(
        type: Type,
        condition: MeetingSearchCondition,
        keyword: String?,
        pageable: Pageable
    ): Slice<MeetingsResponse>

    fun getMeetingsById(meetingId: Long): MeetingsResponse

    fun createMeetings(request: CreateMeetingsRequest): MeetingsResponse

    fun updateMeetings(meetingId: Long, request: UpdateMeetingsRequest): MeetingsResponse

    fun deleteMeetings(meetingId: Long)
}
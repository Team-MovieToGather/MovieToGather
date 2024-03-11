package org.spartaa3.movietogather.domain.meetings.service

import org.spartaa3.movietogather.domain.meetings.dto.meetingsRequest.CreateMeetingsRequest
import org.spartaa3.movietogather.domain.meetings.dto.meetingsRequest.UpdateMeetingsRequest
import org.spartaa3.movietogather.domain.meetings.dto.meetingsResponse.MeetingsResponse

interface MeetingsService {
    // meetingsId: Long으로 전부 수정
    fun getMeetingsById(meetingsId: Long): MeetingsResponse

    fun createMeetings(request: CreateMeetingsRequest): MeetingsResponse

    fun updateMeetings(meetingsId: Long, request: UpdateMeetingsRequest): MeetingsResponse

    fun deleteMeetings(meetingsId: Long)
}
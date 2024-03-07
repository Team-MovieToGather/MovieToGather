package org.spartaa3.movietogather.domain.meetings.service

import org.spartaa3.movietogather.domain.meetings.dto.meetingsRequest.CreateMeetingsRequest
import org.spartaa3.movietogather.domain.meetings.dto.meetingsRequest.UpdateMeetingsRequest
import org.spartaa3.movietogather.domain.meetings.dto.meetingsResponse.MeetingsResponse

interface MeetingsService {

    fun createMeetings(request: CreateMeetingsRequest): MeetingsResponse

    fun updateMeetings(meetingsId: String, request: UpdateMeetingsRequest): MeetingsResponse

    fun deleteMeetings(meetingsId: String)
}
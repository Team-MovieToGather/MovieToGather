package org.spartaa3.movietogather.domain.meetings.service

import org.spartaa3.movietogather.domain.meetings.dto.mettingsRequest.CreateMeetingsRequest
import org.spartaa3.movietogather.domain.meetings.dto.mettingsRequest.UpdateMeetingsRequest

interface MeetingsService {

    fun createMeetings(meetingsId: String, request: CreateMeetingsRequest)

    fun updateMeetings(meetingsId: String, request: UpdateMeetingsRequest)

    fun deleteMeetings(meetingsId: String)
}
package org.spartaa3.movietogather.domain.meetings.repository

import org.spartaa3.movietogather.domain.meetings.entity.MeetingSearchCondition
import org.spartaa3.movietogather.domain.meetings.entity.Meetings
import org.spartaa3.movietogather.domain.meetings.service.Type
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface MeetingQueryRepository {
    fun searchMeeting(
        type: Type,
        condition: MeetingSearchCondition,
        keyword: String?,
        pageable: Pageable
    ): Page<Meetings>
}
package org.spartaa3.movietogather.domain.meetings.repository

import org.spartaa3.movietogather.domain.meetings.entity.Meetings
import org.springframework.data.jpa.repository.JpaRepository

interface MeetingsRepository : JpaRepository<Meetings, Long>, MeetingQueryRepository
package org.spartaa3.movietogather.domain.meetings.repository

import org.spartaa3.movietogather.domain.meetings.entity.MeetingMember
import org.spartaa3.movietogather.domain.meetings.entity.Meetings
import org.spartaa3.movietogather.domain.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MeetingMemberRepository : JpaRepository<MeetingMember, Long> {
    fun findByMeetingsId(meetingId: Long): List<MeetingMember>
}

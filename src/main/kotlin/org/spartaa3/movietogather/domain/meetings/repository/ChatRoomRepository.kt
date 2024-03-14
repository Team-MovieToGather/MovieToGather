package org.spartaa3.movietogather.domain.meetings.repository

import org.spartaa3.movietogather.domain.meetings.entity.ChatRoom
import org.springframework.data.jpa.repository.JpaRepository

interface ChatRoomRepository: JpaRepository<ChatRoom, Long> {
    fun findByMeetingsId(meetingId: Long): ChatRoom?
}
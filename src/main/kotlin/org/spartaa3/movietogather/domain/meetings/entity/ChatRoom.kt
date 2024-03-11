package org.spartaa3.movietogather.domain.meetings.entity

import jakarta.persistence.*

@Entity
class ChatRoom(
    val roomId: String,
    val name: String,
    @OneToOne @JoinColumn(name = "meeting_id") val meetings: Meetings
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}
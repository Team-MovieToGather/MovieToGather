package org.spartaa3.movietogather.domain.meetings.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
data class ChatMessage(
    var type: MessageType?,
    var roomId: String?,
    var sender: String?,
    var message: String?,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    val creatAt: LocalDateTime = LocalDateTime.now()

}
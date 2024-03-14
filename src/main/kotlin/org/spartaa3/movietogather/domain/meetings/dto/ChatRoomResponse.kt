package org.spartaa3.movietogather.domain.meetings.dto

import org.spartaa3.movietogather.domain.meetings.entity.ChatRoom
import org.springframework.web.socket.WebSocketSession

data class ChatRoomResponse(
    val roomId: String,
    val name: String
){
    val sessions: MutableSet<WebSocketSession> = HashSet()
    fun removeSession(session: WebSocketSession) {
        sessions.remove(session)
    }
    companion object{
        fun to(chatRoom: ChatRoom): ChatRoomResponse {
            return ChatRoomResponse(
                roomId = chatRoom.roomId,
                name = chatRoom.name
            )
        }
    }
}
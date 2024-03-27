package org.spartaa3.movietogather.domain.meetings.service

import jakarta.annotation.PostConstruct
import mu.KotlinLogging
import org.spartaa3.movietogather.domain.meetings.dto.ChatRoomResponse
import org.spartaa3.movietogather.domain.meetings.entity.ChatMessage
import org.spartaa3.movietogather.domain.meetings.entity.ChatMessage.Companion.toResponse
import org.spartaa3.movietogather.domain.meetings.entity.ChatRoom
import org.spartaa3.movietogather.domain.meetings.repository.ChatMessageRepository
import org.spartaa3.movietogather.domain.meetings.repository.ChatRoomRepository
import org.spartaa3.movietogather.domain.meetings.repository.MeetingsRepository
import org.spartaa3.movietogather.global.exception.BaseException
import org.spartaa3.movietogather.global.exception.dto.BaseResponseCode
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.socket.WebSocketSession
import java.util.*

@Service
class ChatService(
    private val chatMessageRepository: ChatMessageRepository,
    private val meetingsRepository: MeetingsRepository,
    private val chatRoomRepository: ChatRoomRepository,
) {
    private val log = KotlinLogging.logger {}
    private val chatRooms: MutableMap<String, ChatRoomResponse> = LinkedHashMap()

    @PostConstruct
    private fun init() {
        chatRooms.clear()
    }

    fun findMessage(meetingId: Long): List<ChatMessage> {
        val chatRoom =
            chatRoomRepository.findByMeetingsId(meetingId) ?: throw BaseException(BaseResponseCode.INVALID_MEETING)
        val chatMessage =
            chatMessageRepository.findByRoomId(chatRoom.roomId) ?: throw BaseException(BaseResponseCode.INVALID_MEETING)
        return chatMessage.map { it.toResponse() }
    }

    fun findRoom(meetingId: Long): ChatRoomResponse? {
        val chatRoom = chatRoomRepository.findByMeetingsId(meetingId) ?: throw return null
        return ChatRoomResponse.to(chatRoom)
    }

    fun findRoomById(roomId: String): ChatRoomResponse? {
        return chatRooms[roomId]
    }

    // 채팅방 생성
    fun createRoom(meetingId: Long, name: String): ChatRoomResponse {
        val meetings = meetingsRepository.findByIdOrNull(meetingId)
        val randomId = UUID.randomUUID().toString()
        val chat = chatRoomRepository.findByMeetingsId(meetingId)
        if (chat == null) {
            val chatRoom = chatRoomRepository.save(
                ChatRoom(
                    roomId = randomId,
                    name = name,
                    meetings = meetings!!
                )
            )
            val roomResponse = ChatRoomResponse(
                roomId = chatRoom.roomId,
                name = chatRoom.name
            )
            chatRooms[randomId] = roomResponse
            return roomResponse
        } else {
            throw BaseException(BaseResponseCode.BAD_REQUEST)
        }
    }

    // 메시지 저장
    fun saveMessage(chatMessage: ChatMessage) {
        try {
            chatMessageRepository.save(chatMessage)
        } catch (e: Exception) {
            log.error("Failed to save chat message: {}", e.message)
        }
    }

    // session 삭제
    fun removeSession(session: WebSocketSession) {
        chatRooms.forEach { (_, chatRoom) ->
            chatRoom.removeSession(session)
        }
    }
}

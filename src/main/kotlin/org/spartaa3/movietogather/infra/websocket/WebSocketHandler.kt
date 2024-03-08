package org.spartaa3.movietogather.infra.websocket

import com.fasterxml.jackson.databind.ObjectMapper
import lombok.RequiredArgsConstructor
import mu.KotlinLogging
import org.spartaa3.movietogather.domain.meetings.dto.ChatRoomResponse
import org.spartaa3.movietogather.domain.meetings.entity.ChatMessage
import org.spartaa3.movietogather.domain.meetings.entity.MessageType
import org.spartaa3.movietogather.domain.meetings.service.ChatService
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.io.IOException

@RequiredArgsConstructor
@Component
class WebSocketHandler(
    private val objectMapper: ObjectMapper,
    private val chatService: ChatService,
) : TextWebSocketHandler() {
    private val log = KotlinLogging.logger {}

    // 메시지 전송 메서드
    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val payload: String = message.payload
        log.info("{}", payload)
        val chatMessage: ChatMessage = objectMapper.readValue(payload, ChatMessage::class.java)

        val room: ChatRoomResponse? = chatMessage.roomId?.let { chatService.findRoomById(it) }
        val sessions = room?.sessions ?: mutableSetOf() // 방에 있는 현재 사용자 한명이 WebSocketSession

        when (chatMessage.type) {
            // 입장 메시지
            MessageType.ENTER -> {
                sessions.add(session)
                chatMessage.message = "${chatMessage.sender}님이 입장했습니다."
                sendToEachSocket(sessions, TextMessage(objectMapper.writeValueAsString(chatMessage)))
            }

            // 퇴장 메시지
            MessageType.QUIT -> {
                sessions.remove(session)
                chatMessage.message = "${chatMessage.sender}님이 퇴장했습니다."
                sendToEachSocket(sessions, TextMessage(objectMapper.writeValueAsString(chatMessage)))
            }

            else -> sendToEachSocket(sessions, message) // 입장, 퇴장 아닐 때는 클라이언트로부터 온 메시지 그대로 전달.
        }
        // TALK 메시지 저장
        if (chatMessage.type == MessageType.TALK)
            chatService.saveMessage(chatMessage)
    }

    // websocket 연결 종료 후
    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        chatService.removeSession(session)
    }

    // 채팅방의 webSocket session 에 메시지 전송
    private fun sendToEachSocket(sessions: MutableSet<WebSocketSession>, message: TextMessage) {
        sessions.parallelStream().forEach { roomSession ->
            try {
                roomSession.sendMessage(message)
            } catch (e: IOException) {
                throw RuntimeException()
            }
        }
    }
}
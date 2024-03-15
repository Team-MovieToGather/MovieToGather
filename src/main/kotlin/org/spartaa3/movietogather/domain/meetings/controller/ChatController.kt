package org.spartaa3.movietogather.domain.meetings.controller

import org.spartaa3.movietogather.domain.meetings.dto.ChatRoomResponse
import org.spartaa3.movietogather.domain.meetings.service.ChatService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/meetings/{meetingId}/chat")
class ChatController(private val chatService: ChatService) {

    @PostMapping("/chatRoom")
    fun createRoom(
        @PathVariable meetingId: Long,
        @RequestBody name: String
    ): ResponseEntity<ChatRoomResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(chatService.createRoom(meetingId, name))
    }

    @GetMapping("/chatRoom")
    fun findRoom(@PathVariable meetingId: Long): ResponseEntity<ChatRoomResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(chatService.findRoom(meetingId))
    }
}
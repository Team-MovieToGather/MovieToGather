package org.spartaa3.movietogather.domain.meetings.controller

import org.spartaa3.movietogather.domain.meetings.dto.meetingsRequest.CreateMeetingsRequest
import org.spartaa3.movietogather.domain.meetings.dto.meetingsRequest.UpdateMeetingsRequest
import org.spartaa3.movietogather.domain.meetings.service.MeetingsService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/meetings")
@RestController
class MeetingsController(
    private val meetingsService: MeetingsService
) {
    @PostMapping("/{meetingId}")
    fun createMeetings(
        @PathVariable meetingId: String,
        @RequestBody request: CreateMeetingsRequest,
    ): ResponseEntity<String> {
        meetingsService.createMeetings(request)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body("댓글이 등록되었습니다..")
    }

    @PutMapping("/{meetingId}")
    fun updateMeetings(
        @PathVariable meetingId: String,
        @RequestBody request: UpdateMeetingsRequest,
    ): ResponseEntity<String> {
        meetingsService.updateMeetings(meetingId, request)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("수정이 완료되었습니다.")
    }

    @DeleteMapping("/{meetingId}")
    fun deleteMeetings(
        @PathVariable meetingId: String,
    ): ResponseEntity<String> {
        meetingsService.deleteMeetings(meetingId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body("등록하신 모임을 삭제하였습니다.")
    }
}
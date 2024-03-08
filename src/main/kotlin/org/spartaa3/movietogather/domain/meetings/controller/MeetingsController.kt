package org.spartaa3.movietogather.domain.meetings.controller

import org.spartaa3.movietogather.domain.meetings.dto.meetingsRequest.CreateMeetingsRequest
import org.spartaa3.movietogather.domain.meetings.dto.meetingsRequest.UpdateMeetingsRequest
import org.spartaa3.movietogather.domain.meetings.dto.meetingsResponse.MeetingsResponse
import org.spartaa3.movietogather.domain.meetings.entity.MeetingSearchCondition
import org.spartaa3.movietogather.domain.meetings.service.MeetingsService
import org.spartaa3.movietogather.domain.meetings.service.Type
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/meetings")
@RestController
class MeetingsController(
    private val meetingsService: MeetingsService
) {
    @GetMapping
    fun searchMeeting(
        @RequestParam(name = "type") type: Type,
        @RequestParam(name = "searchCondition") condition: MeetingSearchCondition,
        @RequestParam(name = "keyword") keyword: String?,
        pageable: Pageable
    ): ResponseEntity<Slice<MeetingsResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(meetingsService.searchMeeting(type, condition, keyword, pageable))
    }

    @GetMapping("/{meetingId}")
    fun getMeetingsById(
        @PathVariable meetingId: Long
    ): ResponseEntity<MeetingsResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(meetingsService.getMeetingsById(meetingId))
    }

    @PostMapping
    fun createMeetings(
        @RequestBody request: CreateMeetingsRequest,
    ): ResponseEntity<String> {
        meetingsService.createMeetings(request)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body("댓글이 등록되었습니다..")
    }

    @PutMapping("/{meetingId}")
    fun updateMeetings(
        @PathVariable meetingId: Long,
        @RequestBody request: UpdateMeetingsRequest,
    ): ResponseEntity<String> {
        meetingsService.updateMeetings(meetingId, request)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("수정이 완료되었습니다.")
    }

    @DeleteMapping("/{meetingId}")
    fun deleteMeetings(
        @PathVariable meetingId: Long,
    ): ResponseEntity<String> {
        meetingsService.deleteMeetings(meetingId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body("등록하신 모임을 삭제하였습니다.")
    }
}
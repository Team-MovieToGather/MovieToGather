package org.spartaa3.movietogather.domain.meetings.dto.meetingsRequest

import jakarta.persistence.EnumType
import java.time.LocalDateTime

data class UpdateMeetingsRequest(

    var meetingName: String,

    var movieName: String,

    var startTime: LocalDateTime = LocalDateTime.now(),

    var endTime: LocalDateTime = LocalDateTime.now(),

    val type: EnumType,

    val locationUrl: String,

    val isClosed: Boolean,

    val numApplicants: Long = 1,

    val maxApplicants: Long = 30
)
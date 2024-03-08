package org.spartaa3.movietogather.domain.meetings.dto.meetingsRequest

import org.spartaa3.movietogather.domain.meetings.service.Type
import java.time.LocalDateTime

class CreateMeetingsRequest(

    var meetingName: String,

    var movieName: String,

    var startTime: LocalDateTime,

    var endTime: LocalDateTime,

    val type: Type,

    val locationUrl: String,

    val isClosed: Boolean,

    val numApplicants: Long = 1,

    val maxApplicants: Long = 30

)
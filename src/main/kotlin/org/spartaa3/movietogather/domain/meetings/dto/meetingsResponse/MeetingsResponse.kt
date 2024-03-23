package org.spartaa3.movietogather.domain.meetings.dto.meetingsResponse

import com.fasterxml.jackson.annotation.JsonFormat
import org.spartaa3.movietogather.domain.meetings.service.Type
import java.time.LocalDateTime

class MeetingsResponse(
    val id: Long,
    var meetingName: String,

    var movieName: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm:ss", timezone = "Asia/Seoul")
    var startTime: LocalDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm:ss", timezone = "Asia/Seoul")
    var endTime: LocalDateTime,

    val type: Type,

    val locationUrl: String,

    val isClosed: Boolean,

    val numApplicants: Long = 1,

    val maxApplicants: Long

)

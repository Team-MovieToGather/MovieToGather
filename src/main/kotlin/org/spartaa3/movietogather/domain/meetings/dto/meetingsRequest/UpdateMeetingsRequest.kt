package org.spartaa3.movietogather.domain.meetings.dto.meetingsRequest

import java.time.LocalDateTime

data class UpdateMeetingsRequest(

    var meetingName: String,

    var movieName: String,

    var startTime: LocalDateTime = LocalDateTime.now(),

    var endTime: LocalDateTime = LocalDateTime.now(),

    )
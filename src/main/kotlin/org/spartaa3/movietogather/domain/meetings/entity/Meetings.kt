package org.spartaa3.movietogather.domain.meetings.entity

import jakarta.persistence.*
import org.spartaa3.movietogather.domain.meetings.dto.meetingsResponse.MeetingsResponse
import org.spartaa3.movietogather.domain.meetings.service.Type
import org.spartaa3.movietogather.infra.audit.BaseTimeEntity
import org.spartaa3.movietogather.infra.audit.BaseUserEntity
import java.time.LocalDateTime

@Entity
@Table(name = "meetings")
class Meetings(

    @Column(name = "meeting_Name")
    var meetingName: String,

    @Column(name = "movie_Name")
    var movieName: String,

    @Column(name = "start_time")
    var startTime: LocalDateTime = LocalDateTime.now(),

    @Column(name = "end_time")
    var endTime: LocalDateTime = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    val type: Type,

    @Column(name = "location_Url")
    val locationUrl: String,

    @Column(name = "is_closed")
    var isClosed: Boolean = false,

    @Column(name = "num_applicants")
    var numApplicants: Long,

    @Column(name = "max_applicants")
    val maxApplicants: Long,

    ) : BaseUserEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun isClosed() {
        if (numApplicants >= maxApplicants) {
            isClosed = true
        }
    }


}

fun Meetings.toResponse(): MeetingsResponse {
    return MeetingsResponse(
        id = id!!,
        meetingName = meetingName,
        movieName = movieName,
        startTime = startTime,
        endTime = endTime,
        type = type,
        locationUrl = locationUrl,
        isClosed = isClosed,
        numApplicants = numApplicants,
        maxApplicants = maxApplicants,
    )
}
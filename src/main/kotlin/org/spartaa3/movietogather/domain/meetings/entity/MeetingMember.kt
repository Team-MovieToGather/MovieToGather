package org.spartaa3.movietogather.domain.meetings.entity

import jakarta.persistence.*
import org.spartaa3.movietogather.domain.member.entity.Member

@Entity
data class MeetingMember(
    @ManyToOne
    @JoinColumn(name = "meeting_id")
    var meetings: Meetings,
    @ManyToOne
    @JoinColumn(name = "member_id")
    var member: Member
) {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
package org.spartaa3.movietogather.domain.meetings.repository

import com.querydsl.core.types.dsl.BooleanExpression
import org.spartaa3.movietogather.domain.meetings.entity.MeetingSearchCondition
import org.spartaa3.movietogather.domain.meetings.entity.Meetings
import org.spartaa3.movietogather.domain.meetings.entity.QMeetings
import org.spartaa3.movietogather.domain.meetings.service.Type
import org.spartaa3.movietogather.infra.QueryDslSupport
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl
import org.springframework.stereotype.Repository

@Repository
class MeetingQueryRepositoryImpl : MeetingQueryRepository, QueryDslSupport() {
    private val meetings = QMeetings.meetings
    override fun searchMeeting(
        type: Type,
        condition: MeetingSearchCondition,
        keyword: String?,
        pageable: Pageable
    ): Slice<Meetings> {
        val pageSize = pageable.pageSize
        val contents = queryFactory
            .selectFrom(meetings)
            .where(allCond(type, condition, keyword))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong() + 1)
            .orderBy(meetings.startTime.asc())
            .fetch()

        var hasNext = false
        if (contents.size > pageSize) {
            contents.removeAt(pageSize)
            hasNext = true
        }

        return SliceImpl(contents, pageable, hasNext)
    }

    private fun allCond(type: Type, condition: MeetingSearchCondition, keyword: String?): BooleanExpression? {
        return searchType(type).and(searchKeyword(condition, keyword))
    }

    private fun searchType(type: Type): BooleanExpression {
        return when (type) {
            Type.ALL -> meetings.isNotNull
            Type.ONLINE -> meetings.type.eq(Type.ONLINE)
            Type.OFFLINE -> meetings.type.eq(Type.OFFLINE)
        }
    }

    private fun searchKeyword(condition: MeetingSearchCondition, keyword: String?): BooleanExpression? {
        if (keyword == null) {
            return null
        }
        return when (condition) {
            MeetingSearchCondition.MEETING_TITLE -> meetings.meetingName.like("%$keyword%")
            MeetingSearchCondition.MOVIE_TITLE -> meetings.movieName.like("%$keyword%")
        }
    }

}
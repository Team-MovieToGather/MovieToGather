package org.spartaa3.movietogather.domain.meetings.repository

import com.querydsl.core.types.dsl.BooleanExpression
import org.spartaa3.movietogather.domain.meetings.entity.MeetingSearchCondition
import org.spartaa3.movietogather.domain.meetings.entity.Meetings
import org.spartaa3.movietogather.domain.meetings.entity.QMeetings
import org.spartaa3.movietogather.domain.meetings.service.Type
import org.spartaa3.movietogather.infra.QueryDslSupport
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class MeetingQueryRepositoryImpl : MeetingQueryRepository, QueryDslSupport() {
    private val meetings = QMeetings.meetings
    override fun searchMeeting(
        type: Type,
        condition: MeetingSearchCondition,
        keyword: String?,
        pageable: Pageable
    ): Page<Meetings> {
        val totalCount = queryFactory
            .selectFrom(meetings)
            .where(allCond(type, condition, keyword)).fetch().size.toLong()
        val contents = queryFactory
            .selectFrom(meetings)
            .where(allCond(type, condition, keyword))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong() + 1)
            .orderBy(meetings.startTime.asc())
            .fetch()


        return PageImpl(contents, pageable, totalCount)
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
package org.spartaa3.movietogather.domain.review.repository

import com.querydsl.core.types.dsl.BooleanExpression
import org.spartaa3.movietogather.domain.review.entity.QReview
import org.spartaa3.movietogather.domain.review.entity.Review
import org.spartaa3.movietogather.domain.review.entity.ReviewSearchCondition
import org.spartaa3.movietogather.infra.QueryDslSupport
import org.springframework.data.domain.*
import org.springframework.stereotype.Repository

@Repository
class ReviewQueryRepositoryImpl : ReviewQueryRepository, QueryDslSupport() {
    private val review = QReview.review
    override fun searchReview(
        condition: ReviewSearchCondition,
        keyword: String?,
        pageable: Pageable
    ): Page<Review> {
        val pageSize = pageable.pageSize

        val contents = queryFactory
            .selectFrom(review)
            .where(allCond(condition, keyword))
            .offset(pageable.offset)
            .limit(pageSize.toLong())
            .orderBy(review.createdAt.desc())
            .fetch()

        if (contents.size > pageSize) {
            contents.removeAt(pageSize)

        }
        val total = queryFactory
            .selectFrom(review)
            .where(allCond(condition, keyword))
            .fetchCount()

        return PageImpl(contents, pageable, total)
    }

    private fun allCond(condition: ReviewSearchCondition, keyword: String?): BooleanExpression? {
        if (keyword == null) {
            return review.id.isNotNull
        }
        return when (condition) {
            ReviewSearchCondition.MOVIE_TITLE -> review.movieTitle.like("%$keyword%")
            ReviewSearchCondition.POSTING_TITLE -> review.postingTitle.like("%$keyword%")
        }
    }
}
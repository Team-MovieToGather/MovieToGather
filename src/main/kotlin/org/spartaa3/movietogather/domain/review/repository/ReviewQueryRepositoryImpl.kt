package org.spartaa3.movietogather.domain.review.repository

import com.querydsl.core.types.dsl.BooleanExpression
import org.spartaa3.movietogather.domain.review.entity.QReview
import org.spartaa3.movietogather.domain.review.entity.Review
import org.spartaa3.movietogather.domain.review.entity.ReviewSearchCondition
import org.spartaa3.movietogather.infra.QueryDslSupport
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

class ReviewQueryRepositoryImpl : ReviewQueryRepository, QueryDslSupport() {
    private val review = QReview.review
    override fun searchReview(
        condition: ReviewSearchCondition,
        keyword: String?,
        pageable: Pageable
    ): Page<Review> {
        val totalCount = queryFactory
            .select(review.count())
            .from(review)
            .where(allCond(condition, keyword))
            .fetchOne() ?: 0L

        val contents = queryFactory
            .selectFrom(review)
            .where(allCond(condition, keyword))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(review.createdAt.desc())
            .fetch()

        return PageImpl(contents, pageable, totalCount)
    }

    private fun allCond(condition: ReviewSearchCondition, keyword: String?): BooleanExpression? {
        return postingTitleLike(condition, keyword)
            ?.and(movieTitleLike(condition, keyword))
    }

    private fun postingTitleLike(condition: ReviewSearchCondition, keyword: String?): BooleanExpression? {
        if (condition != ReviewSearchCondition.POSTING_TITLE) return null
        return review.postingTitle.like("%$keyword%")
    }

    private fun movieTitleLike(condition: ReviewSearchCondition, keyword: String?): BooleanExpression? {
        if (condition != ReviewSearchCondition.MOVIE_TITLE) return null
        return review.movieTitle.like("%$keyword%")
    }

}
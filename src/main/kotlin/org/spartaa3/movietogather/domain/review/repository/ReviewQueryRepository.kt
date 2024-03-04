package org.spartaa3.movietogather.domain.review.repository

import org.spartaa3.movietogather.domain.review.entity.Review
import org.spartaa3.movietogather.domain.review.entity.ReviewSearchCondition
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ReviewQueryRepository {
    fun searchReview(condition: ReviewSearchCondition, keyword: String?, pageable: Pageable): Page<Review>
}
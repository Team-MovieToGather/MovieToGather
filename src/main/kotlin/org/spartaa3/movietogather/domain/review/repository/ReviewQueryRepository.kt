package org.spartaa3.movietogather.domain.review.repository

import org.spartaa3.movietogather.domain.review.entity.Review
import org.spartaa3.movietogather.domain.review.entity.ReviewSearchCondition
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface ReviewQueryRepository {
    fun searchReview(condition: ReviewSearchCondition, keyword: String?, pageable: Pageable): Slice<Review>
}
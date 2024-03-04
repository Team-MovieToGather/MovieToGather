package org.spartaa3.movietogather.domain.review.repository

import org.spartaa3.movietogather.domain.review.entity.Review
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewRepository : JpaRepository<Review, Long>, ReviewQueryRepository {

}


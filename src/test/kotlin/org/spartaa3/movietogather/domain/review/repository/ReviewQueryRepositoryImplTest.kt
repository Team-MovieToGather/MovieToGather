package org.spartaa3.movietogather.domain.review.repository

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.spartaa3.movietogather.domain.review.entity.ReviewSearchCondition
import org.spartaa3.movietogather.infra.QueryDslSupport
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.data.domain.Pageable

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(value = [QueryDslSupport::class])
class ReviewQueryRepositoryImplTest @Autowired constructor(
    private val reviewQueryRepository: ReviewQueryRepository
) : FunSpec({

    test("태그가 MOVIE_TITLE이고, 검색한 키워드의 일부가 포함된 제목의 리뷰를 조회한다.") {
        // Given
        val keyword = "Title"
        val tag = ReviewSearchCondition.MOVIE_TITLE
        val pageable = Pageable.ofSize(5)
        // When
        val result = reviewQueryRepository.searchReview(tag,keyword,pageable)
        // Then
        result.forEach {
            it.contents.length shouldBe 1
        }
    }
})
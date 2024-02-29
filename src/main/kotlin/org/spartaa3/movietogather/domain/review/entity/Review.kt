package org.spartaa3.movietogather.domain.review.entity

import jakarta.persistence.*
import org.spartaa3.movietogather.domain.comments.entity.Comments
import org.spartaa3.movietogather.domain.comments.entity.toResponse
import org.spartaa3.movietogather.domain.review.dto.ReviewResponse
import java.time.LocalDateTime

@Entity
@Table(name = "review")
class Review(
    @Column(name = "postingtitle")
    var postingTitle: String,

    @Column(name = "movie_title")
    var movieTitle: String,

    @Column(name = "movie_img")
    var movieImg: String,

    @Column(name = "genre")
    var genre: String,

    @Column(name = "contents")
    var contents: String,

    @Column(name = "star")
    var star: Double,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "modified_at")
    val modifiedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "is_deleted")
    val isDeleted: Boolean = false,
    @OneToMany(mappedBy = "review", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var comments: MutableList<Comments> = mutableListOf()

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

fun Review.toResponse(): ReviewResponse {
    return ReviewResponse(
        id = id!!,
        postingTitle = postingTitle,
        genre = genre,
        star = star,
        movieTitle = movieTitle,
        movieImg = movieImg,
        contents = contents,
        createdAt = createdAt,
        comments = comments.map { it.toResponse() }
    )
}
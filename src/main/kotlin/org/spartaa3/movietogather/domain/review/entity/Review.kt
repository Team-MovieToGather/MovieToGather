package org.spartaa3.movietogather.domain.review.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import org.spartaa3.movietogather.domain.comments.dto.commentsResponse.GetCommentsResponse
import org.spartaa3.movietogather.domain.comments.entity.Comments
import org.spartaa3.movietogather.domain.review.dto.ReviewResponse
import org.spartaa3.movietogather.infra.audit.BaseTimeEntity
import org.spartaa3.movietogather.infra.audit.BaseUserEntity

@Entity
@Table(name = "review"/*, indexes = [Index(name = "idx_movie_title", columnList = "movie_title")]*/)
@JsonIgnoreProperties(ignoreUnknown = true)
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

    @Column(name = "is_deleted")
    val isDeleted: Boolean = false,
    @OneToMany(
        mappedBy = "review",
        fetch = FetchType.EAGER,
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )  //추후 Fetch Join 을 이용하여 구현?
    @JsonIgnore
    var comments: MutableList<Comments> = mutableListOf()

) : BaseUserEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var heart = 0
}

fun Review.toResponse(): ReviewResponse {
    return ReviewResponse(
        id = id ?: 0L,
        postingTitle = postingTitle,
        genre = genre,
        movieTitle = movieTitle,
        movieImg = movieImg,
        contents = contents,
        createdAt = createdAt,
        heart = heart,
        comments = comments.map { GetCommentsResponse.from(it) }
    )
}
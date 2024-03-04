package org.spartaa3.movietogather.domain.api.service

import org.spartaa3.movietogather.domain.api.service.dto.response.MovieResponse
import org.springframework.data.domain.SliceImpl

interface ApiService {
    fun getPopularMoviesList(page: Int): SliceImpl<MovieResponse>
}
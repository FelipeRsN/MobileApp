package com.arctouch.codechallenge.api

import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.model.UpcomingMoviesResponse

interface MoviesDataSource {
    fun getMovieList(page: Long, success : (UpcomingMoviesResponse) -> Unit, failure: (Throwable) -> Unit)
    fun getGenreList(success : (GenreResponse) -> Unit, failure: (Throwable) -> Unit)
}
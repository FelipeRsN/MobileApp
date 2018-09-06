package com.arctouch.codechallenge.api

import android.annotation.SuppressLint
import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MoviesRepository (private val api: TmdbApi): MoviesDataSource {

    @SuppressLint("CheckResult")
    override fun getMovieList(page: Long, success: (UpcomingMoviesResponse) -> Unit, failure: (Throwable) -> Unit) {
        api.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, page, TmdbApi.DEFAULT_REGION)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    success(it)
                }, {
                    failure(it)
                })
    }

    @SuppressLint("CheckResult")
    override fun getGenreList(success: (GenreResponse) -> Unit, failure: (Throwable) -> Unit) {
        api.genres(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                   success(it)
                },{
                    failure(it)
                })
    }
}
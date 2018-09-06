package com.arctouch.codechallenge.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.arctouch.codechallenge.api.MoviesRepository
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.NetworkUtils
import com.arctouch.codechallenge.util.Resource

class MovieListViewModel : ViewModel() {
    private lateinit var repository: MoviesRepository
    private var loadedMovies = ArrayList<Movie>()

    val moviesData = MutableLiveData<Resource<List<Movie>>>()

    fun init(){
        repository = MoviesRepository(NetworkUtils.getRetrofitInstance())
    }

    fun getMoviesData(refreshValues: Boolean = false){
        moviesData.value = Resource.loading()

        if(refreshValues) loadedMovies.clear()

        getGenre()
    }

    private fun getGenre(){
        repository.getGenreList({
            Cache.cacheGenres(it.genres)
            getMovieList()
        },{
            moviesData.value = Resource.error(it)
        })
    }

    private fun getMovieList(){
        repository.getMovieList(1, { it ->
            val moviesWithGenres = it.results.map { movie ->
                movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
            }

            moviesData.value = Resource.success(moviesWithGenres)
        },{
            moviesData.value = Resource.error(it)
        })
    }
}

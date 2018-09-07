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
    private var pageNumber: Long = 1

    val moviesData = MutableLiveData<Resource<ArrayList<Movie>>>()

    //init repository
    fun init(){
        repository = MoviesRepository(NetworkUtils.getRetrofitInstance())
    }

    //setup movies data
    fun getMoviesData(refreshValues: Boolean = false){
        moviesData.value = Resource.loading()

        if(refreshValues) pageNumber = 1

        getGenre()
    }

    //get genre to cache
    private fun getGenre(){
        repository.getGenreList({
            Cache.cacheGenres(it.genres)
            getMovieList()
        },{
            moviesData.value = Resource.error(it)
        })
    }

    //endless scroll
    fun loadMoreMovies(){
        pageNumber++
        getMovieList()
    }

    //get movies list
    private fun getMovieList(){
        repository.getMovieList(pageNumber, { it ->

            val moviesWithGenres = ArrayList<Movie>()
            moviesWithGenres.addAll(it.results.map { movie ->
                movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
            })

            moviesData.value = Resource.success(moviesWithGenres)
        },{
            moviesData.value = Resource.error(it)
        })
    }
}

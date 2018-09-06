package com.arctouch.codechallenge.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.MutableLiveData
import com.arctouch.codechallenge.model.Movie


class NavigationViewModel: ViewModel() {

    enum class AvailableFragments {
        MoviesList,
        MovieDetail
    }

    val fragment = MutableLiveData<AvailableFragments>()
    private var movie: Movie? = null

    fun changeFragmentTo(value: AvailableFragments, movie: Movie){
        fragment.value = value
        this.movie = movie
    }

    fun getMovie(): Movie?{
        return movie
    }

}

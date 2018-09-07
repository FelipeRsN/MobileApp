package com.arctouch.codechallenge.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.SingleLiveEvent


class NavigationViewModel: ViewModel() {

    enum class AvailableFragments {
        MoviesList,
        MovieDetail
    }

    val fragment = MutableLiveData<SingleLiveEvent<AvailableFragments>>()
    private var movie: Movie? = null

    fun changeFragmentTo(value: AvailableFragments, movie: Movie? = null){
        fragment.value = SingleLiveEvent(value)
        this.movie = movie
    }

    fun getMovie(): Movie?{
        return movie
    }


}

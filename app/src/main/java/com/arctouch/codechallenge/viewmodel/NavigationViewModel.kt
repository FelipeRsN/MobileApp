package com.arctouch.codechallenge.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.SingleLiveEvent

//Viewmodel destined to handler the navigation on activity
class NavigationViewModel: ViewModel() {

    //Fragments
    enum class AvailableFragments {
        MoviesList,
        MovieDetail
    }

    val fragment = MutableLiveData<SingleLiveEvent<AvailableFragments>>()
    private var movie: Movie? = null

    //Change the current fragment
    fun changeFragmentTo(value: AvailableFragments, movie: Movie? = null){
        fragment.value = SingleLiveEvent(value)
        this.movie = movie
    }

    //Get movies for movie details screen
    fun getMovie(): Movie?{
        return movie
    }


}

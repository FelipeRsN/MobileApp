package com.arctouch.codechallenge.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import com.arctouch.codechallenge.viewmodel.NavigationViewModel
import kotlinx.android.synthetic.main.movie_detail_fragment.*

class MovieDetailFragment : Fragment() {
    private lateinit var navigationViewModel: NavigationViewModel

    companion object {
        fun newInstance() = MovieDetailFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.movie_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationViewModel = ViewModelProviders.of(activity!!).get(NavigationViewModel::class.java)

        val movie = navigationViewModel.getMovie()

        movie?.let { it ->
            titleTextView.text = it.title
            genresTextView.text = it.genres?.joinToString(separator = ", ") { it.name }
            releaseDateTextView.text = it.releaseDate

            MovieImageUrlBuilder.loadImageForPoster(movie.posterPath, posterImageView)
            MovieImageUrlBuilder.loadImageForBackdrop(movie.backdropPath, movieBanner)

            overviewTextView.text = it.overview
        }


    }
}

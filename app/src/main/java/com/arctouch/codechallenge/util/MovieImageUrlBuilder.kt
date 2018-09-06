package com.arctouch.codechallenge.util

import android.view.View
import android.widget.ImageView
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.api.TmdbApi
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

private val POSTER_URL = "https://image.tmdb.org/t/p/w154"
private val BACKDROP_URL = "https://image.tmdb.org/t/p/w780"

class MovieImageUrlBuilder {
    companion object {
        fun loadImageForBackdrop(path: String?, imageView: ImageView){
            Glide.with(imageView)
                    .load(BACKDROP_URL + path + "?api_key=" + TmdbApi.API_KEY)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(RequestOptions().placeholder(R.drawable.placeholder))
                    .into(imageView)
        }

        fun loadImageForPoster(path: String?, imageView: ImageView){
            Glide.with(imageView)
                    .load(POSTER_URL + path + "?api_key=" + TmdbApi.API_KEY)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(RequestOptions().placeholder(R.drawable.placeholder))
                    .into(imageView)
        }
    }
}

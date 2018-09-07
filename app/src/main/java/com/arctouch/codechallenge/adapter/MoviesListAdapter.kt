package com.arctouch.codechallenge.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import kotlinx.android.synthetic.main.movie_listitem.view.*


class MoviesListAdapter() : RecyclerView.Adapter<MoviesListAdapter.ViewHolder>() {
    private var clickListener: ClickListener? = null
    private lateinit var movies: List<Movie>

    constructor(movies: ArrayList<Movie>) : this() {
        this.movies = movies
        this.clickListener = clickListener
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bind(movie: Movie) {
            itemView.titleTextView.text = movie.title
            itemView.genresTextView.text = movie.genres?.joinToString(separator = ", ") { it.name }
            itemView.releaseDateTextView.text = movie.releaseDate

            MovieImageUrlBuilder.loadImageForPoster(movie.posterPath, itemView.posterImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_listitem, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener{
            clickListener?.onItemClick(position, it, movies[position])
        }

        holder.bind(movies[position])
    }

    fun setOnItemClickListener(clickListener: ClickListener){
        this.clickListener = clickListener
    }

    interface ClickListener {
        fun onItemClick(position: Int, v: View, item: Movie)
    }
}

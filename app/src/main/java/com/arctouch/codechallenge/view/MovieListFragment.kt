package com.arctouch.codechallenge.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.adapter.MoviesListAdapter
import com.arctouch.codechallenge.components.EndlessRecyclerViewScrollListener
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.App
import com.arctouch.codechallenge.util.Resource
import com.arctouch.codechallenge.viewmodel.MovieListViewModel
import com.arctouch.codechallenge.viewmodel.NavigationViewModel
import kotlinx.android.synthetic.main.movie_list_fragment.*


class MovieListFragment : Fragment() {
    private lateinit var viewModel: MovieListViewModel
    private lateinit var navigationViewModel: NavigationViewModel
    private var snackbar: Snackbar? = null
    private lateinit var adapter: MoviesListAdapter
    private var movies = ArrayList<Movie>()
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener

    companion object {
        fun newInstance() = MovieListFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.movie_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MovieListViewModel::class.java)
        navigationViewModel = ViewModelProviders.of(activity!!).get(NavigationViewModel::class.java)
        viewModel.init()

        setupRecyclerViewAndListeners()

        toolbar.title = getString(R.string.app_name)

        viewModel.moviesData.observe(this, Observer { it ->
            it?.let {
                when (it.status) {
                    Resource.Status.SUCCESS ->{
                        movies.addAll(it.data!!)
                        adapter.notifyDataSetChanged()
                        dismissSwipeRefresh()
                        dismissSnackBar()
                    }
                    Resource.Status.LOADING ->{
                        showSwipeRefresh()
                    }
                    Resource.Status.ERROR ->{
                        dismissSwipeRefresh()
                        showSnackBar(it.throwable)
                    }
                }
            }
        })

        viewModel.getMoviesData()
    }

    //Setup recyclerview layout manager and listeners
    private fun setupRecyclerViewAndListeners(){
        //get screen orientation and set gridlayout columns
        val orientation = resources.configuration.orientation

        val layoutManager = if (orientation == Configuration.ORIENTATION_LANDSCAPE)
            GridLayoutManager(activity, 3)
         else
            GridLayoutManager(activity, 2)

        recyclerView.layoutManager = layoutManager
        //

        //adapter setup
        adapter = MoviesListAdapter(movies)
        adapter.setOnItemClickListener(object : MoviesListAdapter.ClickListener{
            override fun onItemClick(position: Int, v: View, item: com.arctouch.codechallenge.model.Movie) {
                navigationViewModel.changeFragmentTo(NavigationViewModel.AvailableFragments.MovieDetail, item)
            }
        })
        recyclerView.adapter = adapter
        //

        //endless scroll listener setup
        scrollListener = object: EndlessRecyclerViewScrollListener(layoutManager){
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                viewModel.loadMoreMovies()
            }
        }
        recyclerView.addOnScrollListener(scrollListener)
        //

        //SwipeToRefresh listener
        swipeToRefresh.setOnRefreshListener {
            viewModel.getMoviesData(true)
            movies.clear()
            scrollListener.resetState()
        }
        //
    }

    //region swipe refresh controller
    private fun showSwipeRefresh(){
        if(!swipeToRefresh.isRefreshing) swipeToRefresh?.isRefreshing = true
    }

    private fun dismissSwipeRefresh(){
        if(swipeToRefresh.isRefreshing) swipeToRefresh?.isRefreshing = false
    }
    //endregion

    //region snackbar controler
    private fun showSnackBar(throwable: Throwable? = null){
        snackbar = if(throwable != null && !throwable.localizedMessage.isNullOrEmpty()){
            Snackbar.make(activity!!.findViewById(android.R.id.content), throwable.localizedMessage, Snackbar.LENGTH_INDEFINITE)
        }else{
            Snackbar.make(activity!!.findViewById(android.R.id.content), getString(R.string.noNetworkAvailable), Snackbar.LENGTH_INDEFINITE)

        }

        snackbar?.setAction(getString(R.string.tryAgain)) {
            viewModel.getMoviesData()
        }

        snackbar?.show()
    }

    private fun dismissSnackBar() {
        if (snackbar != null && snackbar!!.isShown) snackbar?.dismiss()
    }
    //endregion
}

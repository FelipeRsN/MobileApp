package com.arctouch.codechallenge.view

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.viewmodel.NavigationViewModel
import android.arch.lifecycle.ViewModelProviders



class BaseActivity : AppCompatActivity() {

    private lateinit var viewModel: NavigationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_activity)
        viewModel = ViewModelProviders.of(this).get(NavigationViewModel::class.java)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MovieListFragment.newInstance())
                    .commit()
        }

        viewModel.fragment.observe(this, Observer {
            it?.let {
                when(it){
                    NavigationViewModel.AvailableFragments.MoviesList -> {
                        changeFragmentTo(MovieListFragment.newInstance())
                    }

                    NavigationViewModel.AvailableFragments.MovieDetail -> {
                        changeFragmentTo(MovieDetailFragment.newInstance())
                    }
                }
            }
        })
    }

    private fun changeFragmentTo(newFragment: Fragment){
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down, R.anim.slide_in_up, R.anim.slide_out_down)
                .add(R.id.container, newFragment)
                .addToBackStack(null)
                .commit()
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount > 0)
            supportFragmentManager.popBackStack()
        else
            super.onBackPressed()
    }

}

package com.arctouch.codechallenge.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.viewmodel.NavigationViewModel

//BaseActivity destined to control all the navigation between fragments
class BaseActivity : AppCompatActivity() {

    //navigation viewmodel
    private lateinit var viewModel: NavigationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_activity)
        //initialize viewmodel
        viewModel = ViewModelProviders.of(this).get(NavigationViewModel::class.java)

        //first fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MovieListFragment.newInstance())
                    .commit()
        }

        //viewmodel data observer to get next fragment to add
        viewModel.fragment.observe(this, Observer { it ->
            it?.getContentIfNotHandled().let { it ->
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

    //Add fragment
    private fun changeFragmentTo(newFragment: Fragment){
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down, R.anim.slide_in_up, R.anim.slide_out_down)
                .add(R.id.container, newFragment)
                .addToBackStack(null)
                .commit()
    }

    //popback to previous fragment or exit application
    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount > 0)
            supportFragmentManager.popBackStack()
        else
            super.onBackPressed()
    }

}

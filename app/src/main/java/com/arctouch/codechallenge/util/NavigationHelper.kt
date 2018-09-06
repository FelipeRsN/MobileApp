package com.arctouch.codechallenge.util

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity



class NavigationHelper {

    fun getFragmentManager(context: Context): FragmentManager {
        return (context as AppCompatActivity).supportFragmentManager
    }

    fun openFragment(context: Context, frameId: Int, fragment: Fragment) {
        getFragmentManager(context).beginTransaction()
                .replace(frameId, fragment)
                .addToBackStack(null).commit()
    }

    fun addFragment(context: Context, frameId: Int, fragment: Fragment) {
        getFragmentManager(context).beginTransaction()
                .add(frameId, fragment)
                .addToBackStack(null).commit()
    }
}
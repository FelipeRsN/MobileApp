package com.arctouch.codechallenge.util

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class App : Application(){

    companion object STATIC_ITENS {

        const val LOG_ONLINE_REQUEST:String = "ONLINE_REQUEST"

        //Detected if network is available
        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }
}
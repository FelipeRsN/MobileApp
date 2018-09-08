package com.arctouch.codechallenge.util

import com.arctouch.codechallenge.api.TmdbApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

//generate instance of retrofit and reuse as singleton pattern
class NetworkUtils {
    companion object {
        private var instance: Retrofit? = null

        private fun generateRetrofitInstance(): Retrofit {
            val httpClient = OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .readTimeout(90, TimeUnit.SECONDS)
                    .writeTimeout(90, TimeUnit.SECONDS)
                    .connectTimeout(90, TimeUnit.SECONDS)

            return Retrofit.Builder().baseUrl(TmdbApi.URL)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient.build())
                    .build()
        }

        fun getRetrofitInstance(): TmdbApi{
            if(instance == null) instance = generateRetrofitInstance()
            return instance!!.create<TmdbApi>(TmdbApi::class.java)
        }
    }
}
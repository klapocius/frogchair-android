package com.frogchair.android.common.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RestClient {

    private const val URL = "https://something.something" // fill your backend IP

    private val gson: Gson = GsonBuilder().setLenient().create()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun <T> createService(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }

    private val loggedRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .client(OkHttpClient.Builder().addInterceptor(DotInterceptor()).build())
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun <T> createLoggedService(serviceClass: Class<T>): T {
        return loggedRetrofit.create(serviceClass)
    }
}
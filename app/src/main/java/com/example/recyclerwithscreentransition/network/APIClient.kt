package com.example.recyclerwithscreentransition.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {
    private var apiClient: ApiInterface? = null

    @Synchronized
    fun getRetrofitClient(): ApiInterface {
        if (apiClient != null) return apiClient!!
        apiClient = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
        return apiClient!!
    }
}
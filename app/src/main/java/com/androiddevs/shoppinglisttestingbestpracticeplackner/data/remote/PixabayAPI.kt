package com.androiddevs.shoppinglisttestingbestpracticeplackner.data.remote

import com.androiddevs.shoppinglisttestingbestpracticeplackner.BuildConfig
import com.androiddevs.shoppinglisttestingbestpracticeplackner.data.remote.responses.ImageResponse
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayAPI {

    @GET("/api/")
    suspend fun searchForImage(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String = BuildConfig.API_KEY
    ): Response<ImageResponse>
}
package com.skillbox.networking.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("/")
    fun getMovieById(
        @Query("t") id: String,
        @Query("apikey") apiKey: String
    ): Call<List<RemoteMovie>>
}
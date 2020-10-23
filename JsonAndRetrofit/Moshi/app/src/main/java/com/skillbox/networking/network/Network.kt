package com.skillbox.networking.network

import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.skillbox.networking.model.MovieApi
import com.skillbox.networking.model.TypeMovie
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.*
import java.util.concurrent.TimeUnit

object Network {
    const val MOVIE_API_KEY = "6e8c7f7d"

    val flipperNetworkPlugin = NetworkFlipperPlugin()

    val client = OkHttpClient.Builder()
        .retryOnConnectionFailure(true)
        .protocols(listOf(Protocol.HTTP_1_1))
        .connectTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .addNetworkInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.HEADERS)
        )
        .addNetworkInterceptor(FlipperOkhttpInterceptor(flipperNetworkPlugin))
        .build()

    /*private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://www.omdbapi.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun api(): MovieApi {
        return retrofit.create()
    }*/

    fun getSearchMovieCall(text: String, year: Int = 0, type: TypeMovie): Call {
        //http://www.omdbapi.com/?apikey=[yourkey]&s=lord&y=2000&type=
        //type : { movie, series, episode }
        val url = HttpUrl.Builder()
            .scheme("https")
            .host("omdbapi.com")
            .addQueryParameter("t", text)
            //.addQueryParameter("y", if (year == 0) "" else year.toString())
            //.addQueryParameter("type", if (type == TypeMovie.all) "" else type.name)
            .addQueryParameter("apikey", MOVIE_API_KEY)
            .build()

        val request = Request.Builder()
            .get()
            .url(url)
            .build()

        return client.newCall(request)
    }
}
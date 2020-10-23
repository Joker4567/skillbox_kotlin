package com.skillbox.networking.network

import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.skillbox.networking.movie_list.API_KEY
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object Network {

    val flipperNetworkPlugin = NetworkFlipperPlugin()

    val client = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
//        .addNetworkInterceptor(
//            CustomHeaderInterceptor()
//        )
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

    fun getSearchMovieCall(text: String, year: Int = 0, type: TypeMovie): Call {
        //http://www.omdbapi.com/?apikey=[yourkey]&s=lord&y=2000&type=
        //type : { movie, series, episode }
        val url = HttpUrl.Builder()
                .scheme("https")
                .host("omdbapi.com")
                .addQueryParameter("apikey", API_KEY)
                .addQueryParameter("s", text)
                .addQueryParameter("y", if (year == 0) "" else year.toString())
                .addQueryParameter("type", if (type == TypeMovie.all) "" else type.name)
                .build()

        val request = Request.Builder()
                .get()
                .url(url)
                .build()

        return client.newCall(request)
    }

}
package ru.skillbox.flow.utils

import com.facebook.flipper.plugins.network.*
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import ru.skillbox.flow.model.TypeMovie
import java.util.concurrent.TimeUnit

object Network {

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

    fun getSearchMovieCall(text: String, typeMovie: TypeMovie): Call {
        //http://www.omdbapi.com/?apikey=[yourkey]&s=lord&y=2000&type=
        //type : { movie, series, episode }
        val url = HttpUrl.Builder()
                .scheme("https")
                .host("omdbapi.com")
                .addQueryParameter("apikey", API_KEY)
                .addQueryParameter("s", text)
                .addQueryParameter("type", if (typeMovie == TypeMovie.all) "" else typeMovie.name)
                .build()

        val request = Request.Builder()
                .get()
                .url(url)
                .build()

        return client.newCall(request)
    }

}
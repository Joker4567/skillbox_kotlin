package com.skillbox.networking.repository

import android.graphics.Movie
import android.util.Log
import com.skillbox.networking.adapter.MovieCustomAdapter
import com.skillbox.networking.model.MovieApi
import com.skillbox.networking.model.RemoteMovie
import com.skillbox.networking.network.Network
import com.skillbox.networking.model.TypeMovie
import com.skillbox.networking.network.Network.MOVIE_API_KEY
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception

class MovieRepository {

    private val listMovie =
        arrayOf(TypeMovie.all, TypeMovie.episode, TypeMovie.movie, TypeMovie.series)

    //private val presenterScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun searchMovie(
        text: String,
        position: Int,
        year: Int,
        callback: (List<RemoteMovie>) -> Unit,
        errorCallback: (String) -> Unit
    ): Call {
        return Network.getSearchMovieCall(text, year, listMovie[position]).apply {
            enqueue(object : Callback {
                override fun onFailure(call: Call, e: java.io.IOException) {
                    Log.e("Server", "execute request error = ${e.message}", e)
                    errorCallback(e.message.orEmpty())
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val responseString = response.body?.string().orEmpty()
                        if (responseString.contains("Movie not found"))
                            errorCallback("Фильм не найден!")
                        else {
                            val movies = parseMovieResponse(responseString)
                            callback(movies)
                        }
                    } else {
                        errorCallback("Код ошибки: " + response.code)
                    }
                }
            })
        }
    }

    private fun parseMovieResponse(json: String): List<RemoteMovie> {
        return try {
            val moshi = Moshi.Builder()
                .build()

            /*val movieListType = Types.newParameterizedType(
                List::class.java,
                RemoteMovie::class.java
            )*/
            val adapter = moshi.adapter<RemoteMovie>(RemoteMovie::class.java).nonNull()
            try {
                val movies = adapter.fromJson(json)
                return if (movies != null) {
                    //Получаем кастомную модель (преобразованную)
                    //#task_6
                    val custom_movie = customAdapter(movies)
                    listOf(movies)
                } else emptyList()
            } catch (e: JSONException) {
                Log.e("Server", "parse response error = ${e.message}", e)
                emptyList()
            }
        } catch (ex: JsonDataException) {
            Log.e("Server", "error = ${ex.message}", ex)
            emptyList()
        }
    }

    private fun customAdapter(movie: RemoteMovie): MovieCustomAdapter.CustomMovie {
        return MovieCustomAdapter().fromJson(movie)
    }

    fun printJson(movieToSerialize: RemoteMovie): String {
        return try {
            val moshi = Moshi.Builder()
                .add(MovieCustomAdapter())
                .build()
            val adapter = moshi.adapter(RemoteMovie::class.java).nonNull()
            val movieJson = adapter.toJson(movieToSerialize)
            movieJson
        } catch (e: Exception) {
            "parse error = ${e.message}"
        }
    }
}
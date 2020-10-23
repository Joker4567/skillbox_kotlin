package com.skillbox.networking.movie_list

import android.util.Log
import com.skillbox.networking.network.Network
import com.skillbox.networking.network.TypeMovie
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okio.IOException
import org.json.JSONException
import org.json.JSONObject

class MovieRepository {

    private val listMovie = arrayOf(TypeMovie.all, TypeMovie.episode, TypeMovie.movie, TypeMovie.series)

    fun searchMovie(text: String, position:Int, year:Int, callback: (List<RemoteMovie>) -> Unit, errorCallback: (String) -> Unit): Call {
        return Network.getSearchMovieCall(text, year, listMovie[position]).apply {
            enqueue(object : Callback {
                override fun onFailure(call: Call, e: java.io.IOException) {
                    Log.e("Server", "execute request error = ${e.message}", e)
                    errorCallback(e.message.orEmpty())
                }

                override fun onResponse(call: Call, response: Response) {
                    if(response.isSuccessful) {
                        val responseString = response.body?.string().orEmpty()
                        val movies = parseMovieResponse(responseString)
                        callback(movies)
                    } else {
                        errorCallback("Код ошибки: "+response.code)
                    }
                }
            })
        }
    }

    private fun parseMovieResponse(responseBodyString: String): List<RemoteMovie> {
        return try {
            val jsonObject = JSONObject(responseBodyString)
            val movieArray = jsonObject.getJSONArray("Search")

            (0 until movieArray.length()).map { index -> movieArray.getJSONObject(index) }
                .map { movieJsonObject ->
                    val title = movieJsonObject.getString("Title")
                    val year = movieJsonObject.getString("Year")
                    val id = movieJsonObject.getString("imdbID")
                    val posterUrl = movieJsonObject.getString("Poster")
                    val type = movieJsonObject.getString("Type")
                    RemoteMovie(id = id, title = title, year = year, url = posterUrl, type = type)
                }

        } catch (e: JSONException) {
            Log.e("Server", "parse response error = ${e.message}", e)
            emptyList()
        }
    }
}
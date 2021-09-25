package ru.skillbox.flow.data.repository

import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.internal.wait
import org.json.JSONException
import org.json.JSONObject
import ru.skillbox.flow.data.room.MovieDaoEntity
import ru.skillbox.flow.data.room.ProspectorDatabase
import ru.skillbox.flow.data.room.mapToDB
import ru.skillbox.flow.data.room.mapToNetwork
import ru.skillbox.flow.model.Movie
import ru.skillbox.flow.model.TypeMovie
import ru.skillbox.flow.utils.Network
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.suspendCoroutine

class MovieRepository {

    private val movieDao = ProspectorDatabase.instance.movieDao()

    private suspend fun saveMovieList(movies: List<Movie>) {
        movieDao.setMovie(movies.map { it.mapToDB() })
    }

    fun observeMovies(text: String, typeMovie:TypeMovie) : Flow<List<Movie>> =
        movieDao.observeMovies(text, if (typeMovie == TypeMovie.all) "" else typeMovie.name).map { list -> list.map { item -> item.mapToNetwork() } }

    fun searchMovie(text: String, typeMovie:TypeMovie): List<Movie> {
        return try {
            val response = Network.getSearchMovieCall(text, typeMovie).execute()
            if (response.isSuccessful) {
                val responseString = response.body?.string().orEmpty()
                val resultList = parseMovieResponse(responseString)
                CoroutineScope(Dispatchers.IO).launch {
                    saveMovieList(resultList)
                }
                resultList
            } else {
                //запрос к локальной БД для поиска из ранее сохраненного списка
                emptyList()
            }
        } catch (ex: Exception) {
            emptyList()
        }
    }

    private fun parseMovieResponse(responseBodyString: String): List<Movie> {
        if(responseBodyString.isEmpty()) return emptyList()
        return try {
            val jsonObject = JSONObject(responseBodyString)
            if(jsonObject.getBoolean("Response").not()) return emptyList()
            val movieArray = jsonObject.getJSONArray("Search")

            (0 until movieArray.length()).map { index -> movieArray.getJSONObject(index) }
                .map { movieJsonObject ->
                    val title = movieJsonObject.getString("Title")
                    val year = movieJsonObject.getString("Year")
                    val id = movieJsonObject.getString("imdbID")
                    val posterUrl = movieJsonObject.getString("Poster")
                    val type = movieJsonObject.getString("Type")
                    Movie(id = id, title = title, year = year, url = posterUrl, type = type)
                }

        } catch (e: JSONException) {
            Log.e("Server", "parse response error = ${e.message}", e)
            emptyList()
        }
    }
}
package com.skillbox.multithreading.threading

import android.util.Log
import com.skillbox.multithreading.networking.Movie
import com.skillbox.multithreading.networking.Network
import com.skillbox.multithreading.networking.Network.MOVIE_API_KEY
import java.util.Collections

class MovieRepository {

    fun getMovieById(movieId: String): Movie? {
        val response = Network.api().getMovieById(movieId, MOVIE_API_KEY).execute()
        return if(response.isSuccessful)
            response.body()
        else
            null
    }

    fun fetchMovies(
        movieIds: List<String>,
        onMoviesFetched: (movies: List<Movie>, fetchTime: Long) -> Unit
    ) {
        Log.d("ThreadTest", "fetchMovies start on ${Thread.currentThread().name}")
        Thread {

            val startTime = System.currentTimeMillis()
            val allMovies = Collections.synchronizedList(mutableListOf<Movie>())

            val threads = movieIds.chunked(10).map { movieChunk ->
                Thread {
                    val movies = movieChunk.mapNotNull { movieId ->
                        getMovieById(movieId)
                    }
                    allMovies.addAll(movies)
                }
            }

            threads.forEach { it.start() }
            threads.forEach { it.join() }

            val requestTime = System.currentTimeMillis() - startTime

            onMoviesFetched(allMovies, requestTime)
        }.start()
        Log.d("ThreadTest", "fetchMovies end on ${Thread.currentThread().name}")
    }
}
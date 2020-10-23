package com.skillbox.multithreading.threading

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skillbox.multithreading.networking.Movie

class ThreadingViewModel : ViewModel() {

    private val userRepository = MovieRepository()

    private val movieIds = listOf(
        "tt0111161",
        "tt0068646",
        "tt0468569",
        "tt0108052",
        "tt0060196",
        "tt0167260",
        "tt0137523",
        "tt0073486",
        "tt0468569",
        "tt0108052",
        "tt0060196",
        "tt0167260",
        "tt0167260",
        "tt0137523",
        "tt0073486",
        "tt0468569",
        "tt0108052",
        "tt0060196",
        "tt0167260",
        "tt0137523",
        "tt0073486"
    )

    private val timeLiveData = MutableLiveData<Long>()
    private val moviesLiveData = MutableLiveData<List<Movie>>()

    val time: LiveData<Long?>
        get() = timeLiveData

    val movies: LiveData<List<Movie>>
        get() = moviesLiveData

    fun requestMovies() {
        Log.d("ThreadTest", "requestMovies start on ${Thread.currentThread().name}")
        userRepository.fetchMovies(movieIds) { movies, fetchTime ->
            Log.d("ThreadTest", "requestMovies fetched on ${Thread.currentThread().name}")
            timeLiveData.postValue(fetchTime)
            moviesLiveData.postValue(movies)
        }
        Log.d("ThreadTest", "requestMovies end on ${Thread.currentThread().name}")

    }
}
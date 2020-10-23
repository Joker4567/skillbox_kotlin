package com.skillbox.networking.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skillbox.networking.BuildConfig
import com.skillbox.networking.repository.MovieRepository
import com.skillbox.networking.model.RemoteMovie
import com.skillbox.networking.model.Score
import okhttp3.Call

class MovieListViewModel : ViewModel() {

    private val repository = MovieRepository()

    private var currentCall: Call? = null

    private val movieListLiveData = MutableLiveData<List<RemoteMovie>>()
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    private val isErrorLiveData = MutableLiveData<String>()

    val movieList: LiveData<List<RemoteMovie>>
        get() = movieListLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    val isError: LiveData<String>
        get() = isErrorLiveData

    fun search(text: String, positionType:Int, year:Int) {
        isLoadingLiveData.postValue(true)
        currentCall = repository.searchMovie(text, positionType, year,{ movies ->
            isLoadingLiveData.postValue(false)
            movieListLiveData.postValue(movies)
            currentCall = null
        },{error ->
            isLoadingLiveData.postValue(false)
            isErrorLiveData.postValue(error)
            currentCall = null
        })
    }

    fun addRating():Boolean{
        if(movieListLiveData.value != null) {
            if (movieListLiveData.value?.isNotEmpty()!! &&
                movieListLiveData.value?.get(0) != null
            ) {
                movieListLiveData.value?.get(0)?.listRating?.add(
                    Score("Артистизм", "20")
                )
                if (BuildConfig.DEBUG)
                    Log.d(
                        "MovieListViewModel",
                        repository.printJson(movieListLiveData.value?.get(0)!!)
                    )
                movieListLiveData.postValue(movieListLiveData.value)
            }
            return true
        }
        return false
    }

    override fun onCleared() {
        super.onCleared()
        currentCall?.cancel()
    }

}
package com.skillbox.networking.movie_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    override fun onCleared() {
        super.onCleared()
        currentCall?.cancel()
    }

}
package com.skillbox.files.datastore

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.skillbox.data.Repository
import kotlinx.coroutines.launch

class DatastoreViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = Repository(application)

    val textLiveData: LiveData<String>
        get() = repository.observe().asLiveData()

    fun save(text: String) {
        viewModelScope.launch {
            repository.save(text)
        }
    }
}

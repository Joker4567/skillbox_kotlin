package com.example.contentprovider.Data

import android.content.Context
import android.content.Intent
import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException

class LoadViewModel(private val context: Context) : ViewModel() {

    private val repository = Repository(context)

    private var fileName = ""

    private val loadingLiveEvent = MutableLiveData<Boolean>(false)

    private val downloadedLiveEvent = MutableLiveData<Boolean>(false)

    private val intentLiveEvent = MutableLiveData<Intent>()

    val intentLiveData: LiveData<Intent>
        get() = intentLiveEvent

    val downloadedLiveData: LiveData<Boolean>
        get() = downloadedLiveEvent

    val loadingLiveData: LiveData<Boolean>
        get() = loadingLiveEvent

    fun getFile(url: String, context: Context) {
        try {
            fileName = url.substring(url.lastIndexOf('/') + 1)
        } catch (e: Exception) {
        }
        viewModelScope.launch(Dispatchers.IO) {
            if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
                return@launch
            }
            downloadFile(url = url, context = context, fileName = fileName)
        }
    }

    private suspend fun downloadFile(url: String, context: Context, fileName: String) {
        val folder = context.getExternalFilesDir("testFolder")
        val file = File(folder, fileName)
        try {
            loadingLiveEvent.postValue(true)
            file.outputStream().use { fileOutputStream ->
                repository.getFile(url).byteStream().use { inputStream ->
                    inputStream.copyTo(fileOutputStream)
                }
            }
            downloadedLiveEvent.postValue(true)
        } catch (e: IOException) {
            file.delete()
        } finally {
            loadingLiveEvent.postValue(false)
        }
    }

    fun shareFile() {
        viewModelScope.launch(Dispatchers.IO) {
            intentLiveEvent.postValue(repository.shareFile(fileName, context))
        }
    }
}
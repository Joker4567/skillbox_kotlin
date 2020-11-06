package com.skillbox.files.homework

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.skillbox.data.Networking
import com.skillbox.data.Repository
import com.skillbox.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class HomeworkViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = Repository(application)
    private val onErrorLiveData = SingleLiveEvent<String>()
    private val onProgressLiveData = SingleLiveEvent<Boolean>()

    val textLiveData: LiveData<String>
        get() = repository.observe().asLiveData()

    val firstLiveData: LiveData<Boolean>
        get() = repository.observeFirst().asLiveData()

    val onProgress: SingleLiveEvent<Boolean>
        get() = onProgressLiveData

    val onError: SingleLiveEvent<String>
        get() = onErrorLiveData

    fun save(text: String) {
        viewModelScope.launch {
            repository.save(text)
        }
    }

    fun saveFirst(flag:Boolean){
        viewModelScope.launch {
            repository.saveFirst(flag)
        }
    }

    suspend fun downloadFile(testFile: File, textUri: String): Boolean {
        return suspendCoroutine { continuation ->
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    onProgressLiveData.postValue(true)

                    testFile.outputStream().buffered().use { fileOutputStream ->
                        Networking.api
                            .getFile(textUri)
                            .byteStream()
                            .use { inputStream ->
                                inputStream.copyTo(fileOutputStream)
                            }
                    }
                    continuation.resume(true)

                } catch (t: Throwable) {
                    onError.postValue(t.localizedMessage)
                    continuation.resumeWithException(t)
                } finally {
                    onProgressLiveData.postValue(false)
                }
            }
        }
    }
}
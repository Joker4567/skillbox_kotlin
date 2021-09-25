package ru.skillbox.scopedstorage.presentation.docs

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.skillbox.scopedstorage.R
import ru.skillbox.scopedstorage.data.VideosRepository
import ru.skillbox.scopedstorage.utils.SingleLiveEvent
import timber.log.Timber

class DocsViewModel(
    app: Application
) : AndroidViewModel(app) {

    private val videosRepository = VideosRepository(app)

    private val toastSingleLiveEvent = SingleLiveEvent<Int>()
    private val saveSuccessSingleLiveEvent = SingleLiveEvent<Unit>()
    private val loadingMutableLiveData = MutableLiveData<Boolean>(false)

    val toastLiveData: LiveData<Int>
        get() = toastSingleLiveEvent

    val loadingLiveData: LiveData<Boolean>
        get() = loadingMutableLiveData

    val saveSuccessLiveData: LiveData<Unit>
        get() = saveSuccessSingleLiveEvent

    //https://gfycat.com/ru/
    fun saveVideo(name: String, uri: Uri, urlDownload: String) {
        viewModelScope.launch {
            loadingMutableLiveData.postValue(true)
            try {
                videosRepository.saveVideo(name, urlDownload, uri)
                saveSuccessSingleLiveEvent.postValue(Unit)
            } catch (t: Throwable) {
                Timber.e(t)
                toastSingleLiveEvent.postValue(R.string.image_add_error)
            } finally {
                loadingMutableLiveData.postValue(false)
            }
        }
    }

}
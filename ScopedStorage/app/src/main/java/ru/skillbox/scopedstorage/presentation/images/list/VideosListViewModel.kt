package ru.skillbox.scopedstorage.presentation.images.list

import android.app.Application
import android.app.RecoverableSecurityException
import android.app.RemoteAction
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.skillbox.scopedstorage.R
import ru.skillbox.scopedstorage.data.Video
import ru.skillbox.scopedstorage.data.VideosRepository
import ru.skillbox.scopedstorage.utils.SingleLiveEvent
import ru.skillbox.scopedstorage.utils.haveQ
import timber.log.Timber

class VideosListViewModel(
    app: Application
) : AndroidViewModel(app) {

    private val videosRepository = VideosRepository(app)

    private val permissionsGrantedMutableLiveData = MutableLiveData(true)
    private val toastSingleLiveEvent = SingleLiveEvent<Int>()
    private val videosMutableLiveData = MutableLiveData<List<Video>>()
    private val recoverableActionMutableLiveData = MutableLiveData<RemoteAction>()

    private var isObservingStarted: Boolean = false
    private var pendingDeleteId: Long? = null

    val toastLiveData: LiveData<Int>
        get() = toastSingleLiveEvent

    val videosLiveData: LiveData<List<Video>>
        get() = videosMutableLiveData

    val permissionsGrantedLiveData: LiveData<Boolean>
        get() = permissionsGrantedMutableLiveData

    val recoverableActionLiveData: LiveData<RemoteAction>
        get() = recoverableActionMutableLiveData

    override fun onCleared() {
        super.onCleared()
        videosRepository.unregisterObserver()
    }

    fun updatePermissionState(isGranted: Boolean) {
        if(isGranted) {
            permissionsGranted()
        } else {
            permissionsDenied()
        }
    }

    fun permissionsGranted() {
        loadVideos()
        if(isObservingStarted.not()) {
            videosRepository.observeVideo { loadVideos() }
            isObservingStarted = true
        }
        permissionsGrantedMutableLiveData.postValue(true)
    }

    fun permissionsDenied() {
        permissionsGrantedMutableLiveData.postValue(false)
    }

    fun deleteVideo(id: Long) {
        viewModelScope.launch {
            try {
                videosRepository.deleteVideo(id)
                pendingDeleteId = null
            } catch (t: Throwable) {
                Timber.e(t)
                if(haveQ() && t is RecoverableSecurityException) {
                    pendingDeleteId = id
                    recoverableActionMutableLiveData.postValue(t.userAction)
                } else {
                    toastSingleLiveEvent.postValue(R.string.image_list_delete_error)
                }
            }
        }
    }

    fun confirmDelete() {
        pendingDeleteId?.let {
            deleteVideo(it)
        }
    }

    fun declineDelete() {
        pendingDeleteId = null
    }

    private fun loadVideos() {
        viewModelScope.launch {
            try {
                val videos = videosRepository.getVideos()
                videosMutableLiveData.postValue(videos)
            } catch (t: Throwable) {
                Timber.e(t)
                videosMutableLiveData.postValue(emptyList())
                toastSingleLiveEvent.postValue(R.string.image_list_error)
            }
        }
    }
}
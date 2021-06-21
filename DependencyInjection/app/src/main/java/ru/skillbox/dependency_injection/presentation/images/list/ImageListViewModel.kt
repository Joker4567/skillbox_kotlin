package ru.skillbox.dependency_injection.presentation.images.list

import android.app.RecoverableSecurityException
import android.app.RemoteAction
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.skillbox.dependency_injection.R
import ru.skillbox.dependency_injection.data.Image
import ru.skillbox.dependency_injection.data.repository.ImagesRepository
import ru.skillbox.dependency_injection.utils.Observers
import ru.skillbox.dependency_injection.utils.SingleLiveEvent
import ru.skillbox.dependency_injection.utils.haveQ
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val imagesRepository: ImagesRepository,
    private val observer: Observers
) : ViewModel() {

    private val permissionsGrantedMutableLiveData = MutableLiveData(true)
    private val toastSingleLiveEvent = SingleLiveEvent<Int>()
    private val imagesMutableLiveData = MutableLiveData<List<Image>>()
    private val recoverableActionMutableLiveData = MutableLiveData<RemoteAction>()

    private var isObservingStarted: Boolean = false
    private var pendingDeleteId: Long? = null

    val toastLiveData: LiveData<Int>
        get() = toastSingleLiveEvent

    val imagesLiveData: LiveData<List<Image>>
        get() = imagesMutableLiveData

    val permissionsGrantedLiveData: LiveData<Boolean>
        get() = permissionsGrantedMutableLiveData

    val recoverableActionLiveData: LiveData<RemoteAction>
        get() = recoverableActionMutableLiveData

    override fun onCleared() {
        super.onCleared()
        observer.unregisterObserver()
    }

    fun updatePermissionState(isGranted: Boolean) {
        if(isGranted) {
            permissionsGranted()
        } else {
            permissionsDenied()
        }
    }

    fun permissionsGranted() {
        loadImages()
        if(isObservingStarted.not()) {
            observer.observeImages { loadImages() }
            isObservingStarted = true
        }
        permissionsGrantedMutableLiveData.postValue(true)
    }

    fun permissionsDenied() {
        permissionsGrantedMutableLiveData.postValue(false)
    }

    fun deleteImage(id: Long) {
        viewModelScope.launch {
            try {
                imagesRepository.deleteImage(id)
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
            deleteImage(it)
        }
    }

    fun declineDelete() {
        pendingDeleteId = null
    }

    private fun loadImages() {
        viewModelScope.launch {
            try {
                val images = imagesRepository.getImages()
                imagesMutableLiveData.postValue(images)
            } catch (t: Throwable) {
                Timber.e(t)
                imagesMutableLiveData.postValue(emptyList())
                toastSingleLiveEvent.postValue(R.string.image_list_error)
            }
        }
    }
}
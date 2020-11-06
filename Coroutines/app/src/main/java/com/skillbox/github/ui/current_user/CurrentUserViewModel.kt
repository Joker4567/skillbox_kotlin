package com.skillbox.github.ui.current_user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.skillbox.github.models.Following
import com.skillbox.github.models.ProfileUser
import com.skillbox.github.utils.SingleLiveEvent
import kotlinx.coroutines.*

class CurrentUserViewModel : ViewModel() {

    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e("RepositoryListViewModel", "error from CoroutineExceptionHandler", throwable)
    }

    val fragmentIOScope = CoroutineScope(Dispatchers.IO + errorHandler)

    private val repository = CurrentUserRepository()
    private val editProfileLiveEvent = SingleLiveEvent<Boolean>()
    private val profileLiveEvent = SingleLiveEvent<ProfileUser>()
    private val errorLiveEvent = SingleLiveEvent<String>()
    private val followingLiveEvent = SingleLiveEvent<List<Following>>()

    val editProfileLiveData: LiveData<Boolean>
        get() = editProfileLiveEvent

    val profileLiveData: LiveData<ProfileUser>
        get() = profileLiveEvent
    val errorLiveData: LiveData<String>
        get() = errorLiveEvent
    val followingLiveData: LiveData<List<Following>>
        get() = followingLiveEvent

    fun editForm(){
        //Если информация по профилю была получена то true else false
        if(profileLiveData?.value != null)
            editProfileLiveEvent.postValue(true)
        else
            editProfileLiveEvent.postValue(false)
    }

    fun getUserProfile(){
        fragmentIOScope.launch {
            try {
                val user = fragmentIOScope.async {
                    repository.getUserProfile()
                }
                val following = fragmentIOScope.async {
                    repository.getFollowingList()
                }

                user.await()
                following.await()

                profileLiveEvent.postValue(user.getCompleted())
                followingLiveEvent.postValue(following.getCompleted())
            } catch (t:Throwable) {
                errorLiveEvent.postValue(t.message)
                followingLiveEvent.postValue(emptyList())
            }
        }
    }

    override fun onCleared() {
        fragmentIOScope.coroutineContext.cancel()
        super.onCleared()
    }
}
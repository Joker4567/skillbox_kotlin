package com.skillbox.github.ui.current_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.skillbox.github.App
import com.skillbox.github.models.ProfileUser
import com.skillbox.github.utils.SingleLiveEvent
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest

class CurrentUserViewModel : ViewModel() {

    private val repository = CurrentUserRepository()
    private val editProfileLiveEvent = SingleLiveEvent<Boolean>()
    private val profileLiveEvent = SingleLiveEvent<ProfileUser>()
    private val errorLiveEvent = SingleLiveEvent<String>()

    val editProfileLiveData: LiveData<Boolean>
        get() = editProfileLiveEvent

    val profileLiveData: LiveData<ProfileUser>
        get() = profileLiveEvent
    val errorLiveData: LiveData<String>
        get() = errorLiveEvent

    fun editForm(){
        //Если информация по профилю была получена то true else false
        if(profileLiveData?.value != null)
            editProfileLiveEvent.postValue(true)
        else
            editProfileLiveEvent.postValue(false)
    }

    fun getUserProfile(){
        repository.getUserProfile(
            onComplete = { user ->
                profileLiveEvent.postValue(user)
            },
            onError = { throwable ->
                errorLiveEvent.postValue(throwable.message)
            }
        )
    }
}
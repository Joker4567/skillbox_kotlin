package com.prognozrnm.presentation.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.facebook.stetho.inspector.protocol.module.Database
import com.prognozrnm.data.db.ProspectorDatabase
import com.prognozrnm.data.db.entities.UserDaoEntity
import com.prognozrnm.data.repository.LoginRepository
import com.prognozrnm.utils.platform.BaseViewModel

class ProfileViewModel(
    private val loginRepository: LoginRepository
) : BaseViewModel() {

    val eventLogin = MutableLiveData<Boolean>()

    private lateinit var user: UserDaoEntity

    fun exitAccount() {
        launchIO {
            val user = loginRepository.getUserAuth()
            user?.let {
                this.user = UserDaoEntity(
                    it.userId,
                    it.token,
                    it.email,
                    false
                )
                loginRepository.updateUser(
                    UserDaoEntity(
                        user.userId,
                        "",
                        user.email,
                        false
                    ), {
                        Log.d("Profile", "Пользователь успешно вышел из учётной записи")
                        eventLogin.postValue(true)
                    }, ::handleState
                )
            }
        }
    }

//    private fun setUser() {
//        launchIO {
//            loginRepository.setUserLocal(
//                UserDaoEntity(
//                    user.userId,
//                    "",
//                    user.email,
//                    false
//                ), {
//                    Log.d("Profile", "Пользователь успешно вышел из учётной записи")
//                    eventLogin.postValue(true)
//                }, ::handleState
//            )
//        }
//    }
}
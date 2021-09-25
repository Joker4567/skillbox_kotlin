package com.prognozrnm.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.prognozrnm.data.db.entities.UserDaoEntity
import com.prognozrnm.data.entity.CheckList
import com.prognozrnm.data.entity.DataUser
import com.prognozrnm.data.entity.UserInfo
import com.prognozrnm.data.repository.CheckListRepository
import com.prognozrnm.data.repository.LoginRepository
import com.prognozrnm.utils.platform.BaseViewModel
import com.prognozrnm.utils.platform.State
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository
) : BaseViewModel() {

    val userLiveDate = MutableLiveData<UserInfo>()

    fun login(login: String, password: String) {
        launchIO {
            loginRepository.loginAuth(
                login = login,
                password = password,
                onSuccess = ::handleData,
                onState = ::handleState
            )
        }
    }

    private fun handleData(user: UserInfo) {
        launchIO {
            loginRepository.setUserLocal(
                UserDaoEntity(user.data.id, user.token, user.data.email, true),
                { }, ::handleState
            )
        }
        userLiveDate.value = user
    }

    //Попытка авторизации локальной
    fun authorizationAttempt() {
        launch {
            loginRepository.getUserAuth()?.let { user ->
                //TODO токен может быть не валидный, требуется
                // авторизаация для получения корректного токена
                userLiveDate.value = UserInfo(
                    DataUser(
                        user.userId,
                        user.email
                    ),
                    token = user.token
                )
            }
        }
    }
}

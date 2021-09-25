package com.prognozrnm.data.repository

import com.prognozrnm.data.db.dao.UserDao
import com.prognozrnm.data.db.entities.UserDaoEntity
import com.prognozrnm.data.entity.UserInfo
import com.prognozrnm.data.network.ApiService
import com.prognozrnm.utils.platform.BaseRepository
import com.prognozrnm.utils.platform.ErrorHandler
import com.prognozrnm.utils.platform.State
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class LoginRepositoryImp(
    errorHandler: ErrorHandler,
    private val api: ApiService,
    private val userDao: UserDao
) : BaseRepository(errorHandler = errorHandler), LoginRepository {

    override suspend fun loginAuth(
        login: String,
        password: String,
        onSuccess: (UserInfo) -> Unit,
        onState: (State) -> Unit
    ) {
        val jsonObject = JSONObject()
        jsonObject.put("username", login)
        jsonObject.put("password", password)
        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        execute(onSuccess = onSuccess, onState = onState) {
            api.loginAuth(requestBody)
        }
    }

    override suspend fun setUserLocal(
        user: UserDaoEntity,
        onSuccess: (Unit) -> Unit,
        onState: (State) -> Unit
    ) {
        execute(onSuccess = onSuccess, onState = onState) {
            userDao.setUser(user)
        }
    }

    override suspend fun getUserAuth(): UserDaoEntity? = userDao.getUserAuth()

    override suspend fun getUserAuthById(): String?  = userDao.getUserAuthById()

    override suspend fun updateUser(
        user: UserDaoEntity,
        onSuccess: (Unit) -> Unit,
        onState: (State) -> Unit
    ) {
        execute(onSuccess = onSuccess, onState = onState) {
            userDao.updateUser(user)
        }
    }

    override suspend fun removeUserById(
        userId: Int,
        onSuccess: (Unit) -> Unit,
        onState: (State) -> Unit
    ) {
        execute(onSuccess = onSuccess, onState = onState) {
            userDao.removeUserById(userId)
        }
    }
}
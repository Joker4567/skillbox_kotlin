package com.prognozrnm.data.network

import com.prognozrnm.data.entity.UserAssigned
import com.prognozrnm.data.entity.UserInfo
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    //Авторизация пользователя
    @POST("api/Account/login")
    suspend fun loginAuth(@Body requestBody: RequestBody): UserInfo

    //Получение списка работ (заказов)
    @GET("api/CheckList/GetUserAssignedItems")
    suspend fun userAssigned(@Query("userId") idUser: String): UserAssigned

    @POST("api/CheckList/SynchronizeData")
    suspend fun postResult(@Body requestBody: RequestBody): Response<String>
}

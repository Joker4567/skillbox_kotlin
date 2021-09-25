package com.prognozrnm.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserInfo(
    @Json(name = "data")
    val data: DataUser,
    @Json(name = "token")
    val token: String
)
@JsonClass(generateAdapter = true)
data class DataUser(
    @Json(name = "id")
    val id: String,
    @Json(name = "email")
    val email: String
)
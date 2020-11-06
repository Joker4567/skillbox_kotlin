package com.skillbox.github.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Following (
    @Json(name = "avatar_url")
    val avatar_url:String,
    @Json(name = "login")
    val login:String,
    @Json(name = "id")
    val id:Int
)
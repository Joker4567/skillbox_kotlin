package com.skillbox.github.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Repositories(
    @Json(name = "id")
    val id:Int,
    @Json(name = "name")
    val header: String,
    @Json(name = "full_name")
    val fullName: String,
    @Json(name = "owner")
    val owner:ProfileUser,
    @Json(name = "description")
    val description:String? = "",
    @Json(name = "html_url")
    val html_url:String? = "",
    @Json(name = "ssh_url")
    val ssh_url:String? = ""
)
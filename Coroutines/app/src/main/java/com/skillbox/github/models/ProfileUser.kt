package com.skillbox.github.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProfileUser (
    @Json(name = "avatar_url")
    val avatar_url:String,
    @Json(name = "login")
    val login:String,
    @Json(name = "id")
    val id:Int,
    @Json(name = "public_repos")
    val public_repos:Int = 0,
    @Json(name = "name")
    val name:String = "",
    @Json(name = "email")
    val email:String? = "",
    @Json(name = "company")
    val company:String = "",
    @Json(name = "location")
    val city:String = "",
    @Json(name = "url")
    val owner_path:String = "",
    @Json(name = "repos_url")
    val repos_path:String = ""
)
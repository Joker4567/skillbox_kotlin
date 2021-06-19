package com.example.contentprovider.ContentProvider

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    val id: Long,
    val name: String,
    val age: Int
)
package ru.skillbox.notifications.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Sale (
    val title: String,
    val description: String,
    val imageUrl: String?
)
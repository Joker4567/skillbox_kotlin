package ru.skillbox.notifications.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Chat (
    val userId: Long,
    val userName: String,
    val created_at: Long,
    val text: String
)
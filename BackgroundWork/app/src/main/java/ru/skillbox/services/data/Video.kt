package ru.skillbox.services.data

import android.net.Uri

data class Video(
    val id: Long,
    val uri: Uri,
    val name: String,
    val size: Int
)
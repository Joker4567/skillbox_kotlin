package ru.skillbox.dependency_injection.data

import android.net.Uri

data class Image(
    val id: Long,
    val uri: Uri,
    val name: String,
    val size: Int
)
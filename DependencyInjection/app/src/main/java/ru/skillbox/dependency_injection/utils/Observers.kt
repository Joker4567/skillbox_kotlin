package ru.skillbox.dependency_injection.utils

import android.content.Context
import android.database.ContentObserver
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore

class Observers(
        private val context: Context
) {
    private var observer: ContentObserver? = null

    fun observeImages(onChange: () -> Unit) {
        observer = object : ContentObserver(Handler(Looper.getMainLooper())) {
            override fun onChange(selfChange: Boolean) {
                super.onChange(selfChange)
                onChange()
            }
        }
        context.contentResolver.registerContentObserver(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                true,
                observer!!
        )
    }

    fun unregisterObserver() {
        observer?.let { context.contentResolver.unregisterContentObserver(it) }
    }
}
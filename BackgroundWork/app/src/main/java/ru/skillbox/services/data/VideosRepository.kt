package ru.skillbox.services.data

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.skillbox.services.utils.haveQ

class VideosRepository(
    private val context: Context
) {
    // https://filedn.com/ltOdFv1aqz1YIFhf4gTY8D7/ingus-info/BLOGS/Photography-stocks3/stock-photography-slider.jpg

    suspend fun saveVideo(name: String, url: String, uri: Uri? = null) {
        withContext(Dispatchers.IO) {
            val imageUri = saveVideoDetails(name)
            if(uri != null)
                downloadVideo(url, uri)
            else
                downloadVideo(url, imageUri)
            makeVideoVisible(imageUri)
        }
    }

    private fun saveVideoDetails(name: String): Uri {
        val volume = if (haveQ()) {
            MediaStore.VOLUME_EXTERNAL_PRIMARY
        } else {
            MediaStore.VOLUME_EXTERNAL
        }

        val imageCollectionUri = MediaStore.Video.Media.getContentUri(volume)
        val imageDetails = ContentValues().apply {
            put(MediaStore.Video.Media.DISPLAY_NAME, name)
            put(MediaStore.Video.Media.MIME_TYPE, "video/*")
            if (haveQ()) {
                put(MediaStore.Video.Media.IS_PENDING, 1)
            }
        }

        return context.contentResolver.insert(imageCollectionUri, imageDetails)!!
    }

    private fun makeVideoVisible(imageUri: Uri) {
        if(haveQ().not()) return

        val imageDetails = ContentValues().apply {
            put(MediaStore.Video.Media.IS_PENDING, 0)
        }
        context.contentResolver.update(imageUri, imageDetails, null, null)
    }

    private suspend fun downloadVideo(url: String, uri: Uri) {
        context.contentResolver.openOutputStream(uri)?.use { outputStream ->
            Networking.api
                .getFile(url)
                .byteStream()
                .use { inputStream ->
                    inputStream.copyTo(outputStream)
                }
        }
    }
}
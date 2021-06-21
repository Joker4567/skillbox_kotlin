package ru.skillbox.dependency_injection.data.repository

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.skillbox.dependency_injection.data.Api
import ru.skillbox.dependency_injection.data.Image
import ru.skillbox.dependency_injection.utils.haveQ
import javax.inject.Inject

class ImagesRepositoryImpl @Inject constructor(
        private val context: Context,
        private val api: Api
) : ImagesRepository {

    // https://filedn.com/ltOdFv1aqz1YIFhf4gTY8D7/ingus-info/BLOGS/Photography-stocks3/stock-photography-slider.jpg

    override suspend fun getImages(): List<Image> {
        val images = mutableListOf<Image>()
        withContext(Dispatchers.IO) {
            context.contentResolver.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    null,
                    null,
                    null,
                    null
            )?.use { cursor ->
                while (cursor.moveToNext()) {
                    val id = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID))
                    val name =
                            cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME))
                    val size = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.SIZE))

                    val uri =
                            ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)

                    images += Image(id, uri, name, size)
                }
            }
        }
        return images
    }

    override suspend fun saveImage(name: String, url: String) {
        withContext(Dispatchers.IO) {
            val imageUri = saveImageDetails(name)
            downloadImage(url, imageUri)
            makeImageVisible(imageUri)
        }
    }

    private fun saveImageDetails(name: String): Uri {
        val volume = if (haveQ()) {
            MediaStore.VOLUME_EXTERNAL_PRIMARY
        } else {
            MediaStore.VOLUME_EXTERNAL
        }

        val imageCollectionUri = MediaStore.Images.Media.getContentUri(volume)
        val imageDetails = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, name)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            if (haveQ()) {
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }
        }

        return context.contentResolver.insert(imageCollectionUri, imageDetails)!!
    }

    private fun makeImageVisible(imageUri: Uri) {
        if(haveQ().not()) return

        val imageDetails = ContentValues().apply {
            put(MediaStore.Images.Media.IS_PENDING, 0)
        }
        context.contentResolver.update(imageUri, imageDetails, null, null)
    }

    private suspend fun downloadImage(url: String, uri: Uri) {
        context.contentResolver.openOutputStream(uri)?.use { outputStream ->
            api
                    .getFile(url)
                    .byteStream()
                    .use { inputStream ->
                        inputStream.copyTo(outputStream)
                    }
        }
    }

    override suspend fun deleteImage(id: Long) {
        withContext(Dispatchers.IO) {
            val uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
            context.contentResolver.delete(uri, null, null)
        }
    }
}
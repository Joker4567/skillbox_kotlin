package ru.skillbox.services.service

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.skillbox.services.data.VideosRepository
import timber.log.Timber

class DownloadWorker(
    context: Context,
    params: WorkerParameters
): CoroutineWorker(context, params) {

    private val videosRepository = VideosRepository(context)

    override suspend fun doWork(): Result {
        val urlToDownload = inputData.getString(DOWNLOAD_URL_KEY)
        val filename = inputData.getString(NAME_FILE_KEY)
        Timber.d("work started")
        if(urlToDownload.isNullOrEmpty() || filename.isNullOrEmpty()) return Result.failure()
        return withContext(Dispatchers.IO) {
            try {
                videosRepository.saveVideo(filename, urlToDownload)
                Result.success()
            } catch (t: Throwable) {
                Timber.e(t)
                Result.retry()
            }
        }
    }

    companion object {
        const val DOWNLOAD_URL_KEY = "download_url"
        const val NAME_FILE_KEY = "file_name"
    }
}
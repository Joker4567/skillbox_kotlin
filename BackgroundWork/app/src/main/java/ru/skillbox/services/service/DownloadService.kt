package ru.skillbox.services.service

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.skillbox.services.utils.DownloadState
import ru.skillbox.services.utils.NotificationChannels
import timber.log.Timber

class DownloadService: Service() {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onBind(intent: Intent?): IBinder?  = null

    override fun onCreate() {
        super.onCreate()
        Timber.d("onCreate from ${Thread.currentThread().name}")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.d("onStartCommand from ${Thread.currentThread().name}")
        downloadFile()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun downloadFile() {
        startForeground(NOTIFICATION_ID, createNotification(0, 0))
        coroutineScope.launch {
            Timber.d("download started")
            DownloadState.changeDownloadState(true)
            val maxProgress = 5
            (0 until maxProgress).forEach {
                Timber.d("downloadProgress = ${it + 1}/$maxProgress")
                val updatedNotification = createNotification(it + 1, maxProgress)
                NotificationManagerCompat.from(this@DownloadService)
                    .notify(NOTIFICATION_ID, updatedNotification)
                delay(1000)
            }
            Timber.d("download complete")
            DownloadState.changeDownloadState(false)
            stopForeground(true)
            stopSelf()
        }
    }

    private fun createNotification(progress: Int, maxProgress: Int): Notification {
        return NotificationCompat.Builder(this, NotificationChannels.DOWNLOAD_CHANNEL_ID)
            .setContentTitle("Download progress")
            .setSmallIcon(android.R.drawable.stat_sys_download)
            .setProgress(maxProgress, progress, false)
            .setOnlyAlertOnce(true)
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy")
        coroutineScope.cancel()
    }

    companion object {
        private const val NOTIFICATION_ID = 4323
    }
}
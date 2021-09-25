package ru.skillbox.notifications.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat

object NotificationChannels {

    val MESSAGE_CHANNEL_ID = "messages"
    val SALE_CHANNEL_ID = "sale"

    fun create(context: Context) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createMessageChannel(
                context
            )
            createNewsChannel(
                context
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createMessageChannel(context: Context) {
        val name = "Messages"
        val channelDescription = "Urgent messages"
        val priority = NotificationManager.IMPORTANCE_HIGH

        val channel = NotificationChannel(MESSAGE_CHANNEL_ID, name, priority).apply {
            description = channelDescription
            enableVibration(true)
            vibrationPattern = longArrayOf(100, 200, 500, 500)
            setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), null)
        }
        NotificationManagerCompat.from(context).createNotificationChannel(channel)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNewsChannel(context: Context) {
        val name = "News"
        val channelDescription = "App sale messages"
        val priority = NotificationManager.IMPORTANCE_LOW

        val channel = NotificationChannel(SALE_CHANNEL_ID, name, priority).apply {
            description = channelDescription
        }
        NotificationManagerCompat.from(context).createNotificationChannel(channel)
    }


}
package ru.skillbox.notifications.service

import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.skillbox.notifications.R
import ru.skillbox.notifications.activity.MainActivity
import ru.skillbox.notifications.model.Chat
import ru.skillbox.notifications.model.Sale
import ru.skillbox.notifications.model.TypeMessage
import ru.skillbox.notifications.utils.NotificationChannels
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException

class MessagingService : FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val type = TypeMessage.valueOf(remoteMessage.data["type"]?.toString() ?: "")
        if(type == TypeMessage.MESSAGE) {
            val adapter = moshi.adapter(Chat::class.java)
            val data = adapter.fromJson(remoteMessage.data["data"] ?: "")
            data?.let {
                showChatNotification(data.userId.toInt(), data.userName, data.text)
            }
        } else if(type == TypeMessage.SALE) {
            val adapter = moshi.adapter(Sale::class.java)
            val data = adapter.fromJson(remoteMessage.data["data"] ?: "")
            data?.let {
                showSaleNotification(data.title, data.description, data.imageUrl)
            }
        }
    }

    private fun showChatNotification(userId: Int, title: String, message: String) {
        val intent = Intent(this, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(this, 123, intent, 0)

        val notification = NotificationCompat.Builder(this,
            NotificationChannels.MESSAGE_CHANNEL_ID
        )
            .setContentTitle("New message $title")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.drawable.ic_message)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(100, 200, 500, 500))
            .setContentIntent(pendingIntent)
            .build()

        NotificationManagerCompat.from(this)
            .notify(userId, notification)
    }

    private fun showSaleNotification(title: String, message: String, imageUrl: String?) {
        val intent = Intent(this, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(this, 123, intent, 0)

        val notificationBuild = NotificationCompat.Builder(this,
            NotificationChannels.SALE_CHANNEL_ID
        )
            .setContentTitle("Sale $title")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setSmallIcon(R.drawable.ic_notifications)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val notification = if (imageUrl != null) {
            notificationBuild
                .setLargeIcon(loadBitmapWithGlide(imageUrl))
        } else {
            notificationBuild
        }.build()

        NotificationManagerCompat.from(this)
            .notify(SALE_NOTIFICATION_ID, notification)
    }

    private fun loadBitmapWithGlide(url: String) = try {
        Glide.with(this)
            .asBitmap()
            .load(url)
            .error(R.drawable.grapefruit)
            .submit()
            .get()
    } catch (ex: Exception) {
        BitmapFactory.decodeResource(resources, R.drawable.grapefruit)
    }


    companion object {
        const val SALE_NOTIFICATION_ID = 1314
    }
}
package ru.skillbox.notifications

import android.app.Application
import ru.skillbox.notifications.utils.NotificationChannels
import timber.log.Timber

class NotificationApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        NotificationChannels.create(this)
    }

}
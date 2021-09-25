package ru.skillbox.notifications.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log

class NetworkReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        context ?: return

        val conn = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = conn.activeNetworkInfo

        when (networkInfo?.type) {
            ConnectivityManager.TYPE_WIFI -> {
                Log.d("STATE_INT" , "Wi-Fi")
            }
            ConnectivityManager.TYPE_MOBILE -> {
                Log.d("STATE_INT" , "Mobile")
            }
            else -> {

            }
        }
    }
}
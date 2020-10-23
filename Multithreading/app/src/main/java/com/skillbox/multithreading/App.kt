package com.skillbox.multithreading

import android.app.Application
import android.os.StrictMode

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        //Позволяет совершать запросы в интернет на главном потоке
//        StrictMode.setThreadPolicy(
//            StrictMode.ThreadPolicy.Builder()
//                .permitNetwork()
//                .build()
//        )
    }
}
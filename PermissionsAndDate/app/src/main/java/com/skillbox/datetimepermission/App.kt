package com.skillbox.datetimepermission

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.skillbox.datetimepermission.Models.Data

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        app = this
        data = Data.getInstance()
    }

    companion object {
        var activity:MainActivity? = null
        var app:App? = null
        var data: Data? = null
    }
}
package com.skillbox.lists12

import android.app.Application
import com.skillbox.lists12.Fragment.PetListFragment
import com.skillbox.lists12.Models.Data

class App:Application() {

    override fun onCreate() {
        super.onCreate()
        app = this
        data = Data.getInstance()
    }

    companion object {
        var activity:MainActivity? = null
        var fragmentPet:PetListFragment? = null
        var app:App? = null
        var data:Data? = null
    }
}
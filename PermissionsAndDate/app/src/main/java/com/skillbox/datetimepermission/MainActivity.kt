package com.skillbox.datetimepermission

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.skillbox.datetimepermission.Fragments.MessageListFragment

class MainActivity : AppCompatActivity(R.layout.main_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.activity = this
        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MessageListFragment())
                .commit()
        }
    }

}
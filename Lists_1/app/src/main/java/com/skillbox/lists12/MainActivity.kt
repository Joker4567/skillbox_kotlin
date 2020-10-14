package com.skillbox.lists12

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.skillbox.lists12.Fragment.PetListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PetListFragment(),"PersonListFragment")
                .commit()
        }
    }
}

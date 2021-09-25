package ru.skillbox.flow.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.skillbox.flow.R
import ru.skillbox.flow.data.room.ProspectorDatabase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onDestroy() {
        ProspectorDatabase.instance.close()
        super.onDestroy()
    }
}
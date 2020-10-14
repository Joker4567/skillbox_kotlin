package com.pstu.hello

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Установка activity_main.xml экраном, видному пользователю
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.textViewSettings)
        textView.text = """
                BuildType=${BuildConfig.BUILD_TYPE}
                Flovor=${BuildConfig.FLAVOR}
                VersionName=${BuildConfig.VERSION_NAME}
                VersionCode=${BuildConfig.VERSION_CODE}
                AppId=${BuildConfig.APPLICATION_ID}
            """.trimIndent()
    }
}

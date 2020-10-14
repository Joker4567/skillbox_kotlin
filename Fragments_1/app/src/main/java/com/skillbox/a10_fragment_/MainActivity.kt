package com.skillbox.a10_fragment_

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.skillbox.a10_fragment_.helper.MainListener
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(R.layout.activity_main), MainListener {

    var state = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var checkMainFragment = false
        supportFragmentManager.fragments.forEach{
            if(it is MainFragment) checkMainFragment = true
        }
        if(!checkMainFragment) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LoginFragment())
                .addToBackStack("LoginFragment")
                .commit()
        }
    }

    override fun onBackPressed() {
        if (state.contains("[MainFragment] onStart"))
            exitProcess(-1)
        else if (state.contains("[MainFragment]") || state.contains("[DetailFragment]"))
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment())
                .apply {
                    addToBackStack("MainFragment")
                }
                .commit()
        else
            super.onBackPressed()
    }

    override fun onClick(state: String) {
        this.state = state
    }
}

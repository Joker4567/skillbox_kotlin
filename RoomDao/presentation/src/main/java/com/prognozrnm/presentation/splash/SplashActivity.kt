package com.prognozrnm.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.prognozrnm.presentation.MainActivity
import com.prognozrnm.presentation.R
import com.prognozrnm.utils.ext.gone
import com.prognozrnm.utils.ext.observe
import com.prognozrnm.utils.ext.observeEvent
import com.prognozrnm.utils.ext.show
import com.prognozrnm.utils.platform.BaseActivity
import com.prognozrnm.utils.platform.State
import kotlinx.android.synthetic.main.activity_splash.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity(R.layout.activity_splash) {

    override val statusBarColor: Int
        get() = R.color.colorPrimary

    override val statusBarLightMode: Boolean
        get() = false

    override val screenViewModel by viewModel<SplashViewModel>()

    override fun initInterface(savedInstanceState: Bundle?) {
        btRetry.gone()
        btRetry.setOnClickListener {
            btRetry.gone()
            screenViewModel.getCheckList()
        }
        observeEvent(screenViewModel.eventLoader) {
            if (it is State.Loaded)
                Handler().postDelayed({ startApp() }, 400)
        }
        observe(screenViewModel.eventButton){
            if(it!!) btRetry.show() else btRetry.gone()
        }
        observe(screenViewModel.eventStartApp){
            if(it!!) Handler().postDelayed({ startApp() }, 400)
        }
    }

    private var isActive = false
    private fun startApp() {
        if(isActive) return
        isActive = true
        val newIntent = Intent(this, MainActivity::class.java)
        startActivity(newIntent)
        finish()
    }
}
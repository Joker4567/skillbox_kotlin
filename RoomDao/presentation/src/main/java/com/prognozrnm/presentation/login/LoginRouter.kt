package com.prognozrnm.presentation.login

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.prognozrnm.presentation.R

interface LoginRoutingLogic {
    fun routeToMain()
}

class LoginRouter(val fragment: Fragment) : LoginRoutingLogic {

    override fun routeToMain() {
        fragment
            .findNavController()
            .navigate(R.id.action_loginFragment_to_navigation)
    }
}
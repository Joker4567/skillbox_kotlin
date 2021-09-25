package com.prognozrnm.presentation.profile

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.prognozrnm.presentation.R
import com.prognozrnm.presentation.profile.ProfileFragmentDirections.Companion.actionProfileFragmentToLogin

interface ProfileRouterLogic {
    fun routeToLogin()
}

class ProfileRouter(val fragment: Fragment) : ProfileRouterLogic {

    override fun routeToLogin() {
        fragment
            .findNavController()
            .navigate(R.id.action_profileFragment_to_login)
    }
}
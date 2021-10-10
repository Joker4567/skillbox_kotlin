package com.skillbox.presentation.menu

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

interface MenuRouterLogic {
    fun routerBack()
}

class MenuRouter(val fragment: Fragment) : MenuRouterLogic {

    override fun routerBack() {
        fragment
                .findNavController()
                .navigateUp()
    }
}
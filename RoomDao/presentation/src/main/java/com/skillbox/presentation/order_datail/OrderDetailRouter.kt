package com.skillbox.presentation.order_datail

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

interface OrderDetailRouterLogic {
    fun routerNavigateUp()
}

class OrderDetailRouter(val fragment: Fragment) : OrderDetailRouterLogic {

    override fun routerNavigateUp() {
        fragment
            .findNavController()
                .navigateUp()
    }
}
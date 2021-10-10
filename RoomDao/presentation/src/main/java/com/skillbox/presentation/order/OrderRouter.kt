package com.skillbox.presentation.order

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

interface OrderRouterLogic {
    fun routeDetailOrder(orderId: Int)
}

class OrderRouter(val fragment: Fragment) : OrderRouterLogic {

    override fun routeDetailOrder(orderId: Int) {
        val action = OrderFragmentDirections.actionOrderFragmentToOrderDetailFragment(orderId)
        fragment
            .findNavController()
                .navigate(action)
    }
}
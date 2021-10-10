package com.skillbox.presentation.lenta

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

interface LentaRouterLogic {
    fun routeToMenuRestaurant(restaurantId: Int)
}

class LentaRounter(val fragment: Fragment) : LentaRouterLogic {

    override fun routeToMenuRestaurant(restaurantId: Int) {
        val action =
            LentaFragmentDirections.actionLentaFragmentToMenuFragment(restaurantId)
        fragment
            .findNavController()
            .navigate(action)
    }
}
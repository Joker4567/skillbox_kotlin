package com.prognozrnm.presentation.detail_work

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.prognozrnm.presentation.R
import com.prognozrnm.presentation.home.HomeFragmentDirections

interface DetailWorkRouterLogic {
    fun routeToHome()
    fun routeToGeoMap(idResult:Int)
}

class DetailWorkRouter(val fragment: Fragment) : DetailWorkRouterLogic {

    override fun routeToHome() {
        fragment
            .findNavController()
            .navigateUp()
    }

    override fun routeToGeoMap(idResult:Int) {
        val action =
            DetailWorkFragmentDirections.actionDetailWorkFragmentToGeoLocationFragment(idResult)
        fragment
            .findNavController()
            .navigate(action)
    }
}

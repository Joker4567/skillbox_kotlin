package com.prognozrnm.presentation.geo_location

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.prognozrnm.presentation.R

interface GeoLocationLogic {
    fun routeToWorkCheckList()
}

class GeoLocationRouter(val fragment: Fragment) : GeoLocationLogic {

    override fun routeToWorkCheckList() {
        fragment
            .findNavController()
            .navigateUp()
    }
}
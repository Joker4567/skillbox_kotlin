package com.prognozrnm.presentation.home

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.prognozrnm.data.entity.WorkList.Work

interface HomeRouterLogic {
    fun routeToDetail(work: Work)
}

class HomeRouter(val fragment: Fragment) : HomeRouterLogic {

    override fun routeToDetail(work: Work) {
        val action =
            HomeFragmentDirections.actionHomeFragmentToDetailWorkFragment(work)
        fragment
            .findNavController()
            .navigate(action)
    }
}
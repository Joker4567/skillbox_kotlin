package com.prognozrnm.presentation.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.prognozrnm.presentation.R
import com.prognozrnm.utils.ext.setupBottomWithNavController
import com.prognozrnm.utils.platform.BaseFragment
import kotlinx.android.synthetic.main.fragment_container.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment(R.layout.fragment_main) {

    private var currentNavController: LiveData<NavController>? = null
    override val screenViewModel by viewModel<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        val navGraphIds = listOf(
            R.navigation.home,
            R.navigation.profile,
            R.navigation.send
        )

        val controller = bottom_nav.setupBottomWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = requireActivity().supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = requireActivity().intent
        )

        currentNavController = controller
    }
}
package com.prognozrnm.presentation.profile

import android.os.Bundle
import com.prognozrnm.presentation.R
import com.prognozrnm.utils.ext.gone
import com.prognozrnm.utils.ext.observeFragment
import com.prognozrnm.utils.platform.BaseFragment
import kotlinx.android.synthetic.main.fragment_container.*
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {
    override val toolbarTitle: String = "Профиль"
    override val statusBarColor: Int = R.color.colorTransparent
    override val statusBarLightMode: Boolean = true
    override val setToolbar: Boolean = true
    override val setDisplayHomeAsUpEnabled: Boolean = false
    override val screenViewModel by viewModel<ProfileViewModel>()
    private lateinit var router: ProfileRouterLogic

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        router = ProfileRouter(this)
        btExitAccount.setOnClickListener {
            screenViewModel.exitAccount()
        }
        observeFragment(screenViewModel.eventLogin) {
            if (it!!) {
                router.routeToLogin()
            }
        }
    }
}
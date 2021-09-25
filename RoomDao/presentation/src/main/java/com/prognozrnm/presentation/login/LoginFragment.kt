package com.prognozrnm.presentation.login

import android.os.Bundle
import com.prognozrnm.data.entity.UserInfo
import com.prognozrnm.data.storage.Pref
import com.prognozrnm.presentation.R
import com.prognozrnm.utils.ext.observeFragment
import com.prognozrnm.utils.platform.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment(R.layout.fragment_login) {

    override val toolbarTitle: String = "Авторизация"
    override val statusBarColor: Int = R.color.colorTransparent
    override val statusBarLightMode: Boolean = true
    override val setToolbar: Boolean = true
    override val setDisplayHomeAsUpEnabled: Boolean = false
    override val screenViewModel by viewModel<LoginViewModel>()

    private lateinit var router: LoginRoutingLogic

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        router = LoginRouter(this)
        login_btnLogin.setOnClickListener {
            screenViewModel.login(
                login_editLogin.text?.trim().toString(),
                login_editPassword.text?.trim().toString()
            )
        }
        observeFragment(screenViewModel.userLiveDate, ::handleUser)
        screenViewModel.authorizationAttempt()
    }

    private fun handleUser(user: UserInfo?){
        user?.let {
            Pref(requireContext()).authToken = user.token
            router.routeToMain()
        }
    }
}
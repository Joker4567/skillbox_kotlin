package com.prognozrnm.prospector.di.module

import com.prognozrnm.data.entity.WorkList.Work
import com.prognozrnm.presentation.detail_work.DetailWorkViewModel
import com.prognozrnm.presentation.geo_location.GeoLocationViewModel
import com.prognozrnm.presentation.home.HomeViewModel
import com.prognozrnm.presentation.login.LoginViewModel
import com.prognozrnm.presentation.main.MainViewModel
import com.prognozrnm.presentation.profile.ProfileViewModel
import com.prognozrnm.presentation.send.SendViewModel
import com.prognozrnm.presentation.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { MainViewModel() }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { SendViewModel(get(), get(), get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { (workItem: Work) -> DetailWorkViewModel(workItem, get(), get()) }
    viewModel { (idResult:Int) -> GeoLocationViewModel(idResult, get()) }
}
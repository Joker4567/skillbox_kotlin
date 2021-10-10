package com.skillbox.room.di.module

import com.skillbox.presentation.lenta.LentaViewModel
import com.skillbox.presentation.main.MainViewModel
import com.skillbox.presentation.menu.MenuViewModel
import com.skillbox.presentation.order.OrderViewModel
import com.skillbox.presentation.order_datail.OrderDetailViewModel
import com.skillbox.presentation.rating.RatingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { MainViewModel(get(), get(), get(), get(), get()) }
    viewModel { OrderViewModel(get()) }
    viewModel { RatingViewModel(get(), get()) }
    viewModel { MenuViewModel(get()) }
    viewModel { OrderDetailViewModel(get()) }
    viewModel { LentaViewModel(get()) }
//    viewModel { (item: Object) -> ViewModel(item, get()) }
}
package com.skillbox.room.di.module

import com.skillbox.data.repository.menu.MenuRepository
import com.skillbox.data.repository.menu.MenuRepositoryImp
import com.skillbox.data.repository.order.OrderRepository
import com.skillbox.data.repository.order.OrderRepositoryImpl
import com.skillbox.data.repository.rating.RatingRepository
import com.skillbox.data.repository.rating.RatingRepositoryImpl
import com.skillbox.data.repository.restaurant.RestaurantRepository
import com.skillbox.data.repository.restaurant.RestaurantRepositoryImpl
import com.skillbox.data.repository.tape.TapeRepository
import com.skillbox.data.repository.tape.TapeRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<MenuRepository>(createdAtStart = true, override = true) {
        MenuRepositoryImp(get())
    }
    single<RestaurantRepository>(createdAtStart = true, override = true) {
        RestaurantRepositoryImpl(get())
    }
    single<TapeRepository>(createdAtStart = true, override = true) {
        TapeRepositoryImpl(get())
    }
    single<OrderRepository>(createdAtStart = true, override = true) {
        OrderRepositoryImpl(get())
    }
    single<RatingRepository>(createdAtStart = true, override = true) {
        RatingRepositoryImpl(get())
    }
}

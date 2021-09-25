package com.prognozrnm.prospector.di.module

import android.content.Context
import com.google.gson.Gson
import com.prognozrnm.utils.platform.ErrorHandler
import com.prognozrnm.utils.platform.NetworkHandler
import org.koin.dsl.module

val appModule = module {

    single { provideNetworkHandler(get()) }

    single { provideErrorHandler(get()) }
}

fun provideNetworkHandler(context: Context): NetworkHandler {
    return NetworkHandler(context)
}

fun provideErrorHandler(networkHandler: NetworkHandler): ErrorHandler {
    return ErrorHandler(networkHandler, Gson())
}
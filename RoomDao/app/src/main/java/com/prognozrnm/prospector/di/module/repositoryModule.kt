package com.prognozrnm.prospector.di.module

import com.prognozrnm.data.network.ApiService
import com.prognozrnm.data.repository.*
import com.prognozrnm.prospector.BuildConfig
import com.prognozrnm.utils.platform.ErrorHandler
import org.koin.dsl.module

val repositoryModule = module {
    single<LoginRepository>(createdAtStart = true, override = true) {
        LoginRepositoryImp(get<ErrorHandler>(), get<ApiService>(), get())
    }
    single<CheckListRepository>(createdAtStart = true, override = true) {
        CheckListRepositoryImp(get(), get(), BuildConfig.API_ENDPOINT, get())
    }
    single<UserAssignedRepository>(createdAtStart = true, override = true) {
        UserAssignedRepositoryImp(get<ErrorHandler>(), get<ApiService>(), get())
    }
    single<ResultRepository>(createdAtStart = true, override = true) {
        ResultRepositoryImp(get(),get(),get(), get())
    }
}

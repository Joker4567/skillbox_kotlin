package com.prognozrnm.prospector.di.module

import android.content.Context
import com.prognozrnm.data.db.ProspectorDatabase
import org.koin.dsl.module

val roomModule = module {

    single { provideDatabase(get()) }
    single { CheckListDao(get()) }
    single { checkUserDao(get()) }
    single { userAssignedDao(get()) }
    single { resultDao(get()) }
}

fun provideDatabase(context: Context) = ProspectorDatabase.buildDataSource(context)

fun CheckListDao(prospectorDatabase: ProspectorDatabase) = prospectorDatabase.checkListDao()
fun checkUserDao(prospectorDatabase: ProspectorDatabase) = prospectorDatabase.userDao()
fun userAssignedDao(prospectorDatabase: ProspectorDatabase) = prospectorDatabase.userAssignedDao()
fun resultDao(prospectorDatabase: ProspectorDatabase) = prospectorDatabase.resultDao()

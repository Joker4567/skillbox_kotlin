package ru.skillbox.dependency_injection.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.skillbox.dependency_injection.utils.Observers

@InstallIn(SingletonComponent::class)
@Module
object ObserverModule {

    @Provides
    fun providesObserver(@ApplicationContext context: Context): Observers {
        return Observers(context)
    }
}
package ar.edu.unlam.mobile.scaffolding.data.di

import android.content.Context
import ar.edu.unlam.mobile.scaffolding.data.local.DataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun provideDataStoreManager(
        @ApplicationContext context: Context,
    ): DataStoreManager {
        val manager = DataStoreManager
        manager.initialize(context)
        return manager
    }
}

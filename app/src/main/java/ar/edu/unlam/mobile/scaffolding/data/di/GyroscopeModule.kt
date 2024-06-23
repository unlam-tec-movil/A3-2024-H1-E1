package ar.edu.unlam.mobile.scaffolding.data.di

import android.content.Context
import ar.edu.unlam.mobile.scaffolding.data.sensors.GyroscopeSensor
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GetGyroscopeDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GyroscopeModule {
    @Provides
    @Singleton
    fun provideGyroscopeSensor(
        @ApplicationContext context: Context,
    ): GyroscopeSensor {
        return GyroscopeSensor(context)
    }

    @Provides
    @Singleton
    fun provideGetGyroscopeDataUseCase(gyroscopeSensor: GyroscopeSensor): GetGyroscopeDataUseCase {
        return GetGyroscopeDataUseCase(gyroscopeSensor)
    }
}

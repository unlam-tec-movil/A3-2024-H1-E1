package ar.edu.unlam.mobile.scaffolding.domain.di

import android.content.Context
import ar.edu.unlam.mobile.scaffolding.domain.services.LocationService
import ar.edu.unlam.mobile.scaffolding.domain.services.LocationServiceImpl
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {
    @Singleton
    @Provides
    fun provideLocationService(
        @ApplicationContext context: Context,
    ): LocationService = LocationServiceImpl(context, LocationServices.getFusedLocationProviderClient(context))
}

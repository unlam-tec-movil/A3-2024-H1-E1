package ar.edu.unlam.mobile.scaffolding.domain.di

import ar.edu.unlam.mobile.scaffolding.data.repository.StorageRepository
import ar.edu.unlam.mobile.scaffolding.domain.services.StorageService
import ar.edu.unlam.mobile.scaffolding.domain.usecases.DeleteImage
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GetAllImages
import ar.edu.unlam.mobile.scaffolding.domain.usecases.UploadImage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object StorageProviderRepository {
    @Provides
    fun StorageProviderRepository(storageRepository: StorageRepository): StorageService  {
        return StorageService(storageRepository)
    }

    @Provides
    fun provideGetAllImages(storageService: StorageService): GetAllImages  {
        return storageService
    }

    @Provides
    fun provideUploadImage(storageService: StorageService): UploadImage  {
        return storageService
    }

    @Provides
    fun provideDeleteImage(storageService: StorageService): DeleteImage  {
        return storageService
    }
}

package ar.edu.unlam.mobile.scaffolding.domain.di

import ar.edu.unlam.mobile.scaffolding.data.repository.StorageRepositoryInterface
import ar.edu.unlam.mobile.scaffolding.domain.services.StorageService
import ar.edu.unlam.mobile.scaffolding.domain.usecases.DeleteImage
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GetAllImagesForPublication
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GetAllImagesFromUrl
import ar.edu.unlam.mobile.scaffolding.domain.usecases.UploadImage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object StorageProviderRepository {
    @Provides
    fun StorageProviderRepository(storageRepository: StorageRepositoryInterface): StorageService = StorageService(storageRepository)

    @Provides
    fun provideUploadImage(storageService: StorageService): UploadImage = storageService

    @Provides
    fun provideGetAllImagesForPublication(storageService: StorageService): GetAllImagesForPublication = storageService

    @Provides
    fun provideDeleteImage(storageService: StorageService): DeleteImage = storageService

    @Provides
    fun provideGetAllImageFromUrl(storageService: StorageService): GetAllImagesFromUrl = storageService
}

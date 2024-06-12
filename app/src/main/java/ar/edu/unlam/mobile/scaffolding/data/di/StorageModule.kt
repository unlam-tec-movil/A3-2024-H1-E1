package ar.edu.unlam.mobile.scaffolding.data.di

import ar.edu.unlam.mobile.scaffolding.data.network.StorageNetworkImpl
import ar.edu.unlam.mobile.scaffolding.data.network.StorageNetworkInterface
import ar.edu.unlam.mobile.scaffolding.data.repository.StorageRepository
import ar.edu.unlam.mobile.scaffolding.data.repository.StorageRepositoryInterface
import com.google.firebase.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {
    @Provides
    fun getStorageProvide() = Firebase.storage

    @Provides
    fun getStorageNetworkImpl(storage: FirebaseStorage): StorageNetworkInterface =
        StorageNetworkImpl(
            storage = storage,
        )

    @Provides
    fun provideStorageRepositoryInterface(storageRe: StorageNetworkInterface): StorageRepositoryInterface = StorageRepository(storageRe)
}

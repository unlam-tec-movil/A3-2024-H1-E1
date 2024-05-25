package ar.edu.unlam.mobile.scaffolding.data.repository

import android.graphics.Bitmap
import ar.edu.unlam.mobile.scaffolding.data.network.StorageNetworkInterface
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StorageRepository
    @Inject
    constructor(
        private val storageNetwork: StorageNetworkInterface,
    ) : StorageRepositoryInterface {
        override suspend fun getStorageReference(userId: String): StorageReference {
            return storageNetwork.getStorageReference(userId)
        }

        override suspend fun getAllImages(userId: String): Flow<List<Bitmap>> {
            return storageNetwork.getAllImages(userId)
        }

        override suspend fun uploadImage(
            image: Bitmap,
            userId: String,
        ) {
            storageNetwork.uploadImage(image, userId)
        }

        override suspend fun deleteImage(imageUrl: String) {
            TODO("Not yet implemented")
        }
    }

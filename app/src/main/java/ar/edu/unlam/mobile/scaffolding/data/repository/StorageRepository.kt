package ar.edu.unlam.mobile.scaffolding.data.repository

import android.graphics.Bitmap
import ar.edu.unlam.mobile.scaffolding.data.network.StorageNetworkInterface
import javax.inject.Inject

class StorageRepository @Inject constructor(
    private val storageNetwork: StorageNetworkInterface,
) : StorageRepositoryInterface {
    override suspend fun uploadImage(
        image: Bitmap,
        userId: String,
        publicationId: String,
    ): String {
        return storageNetwork.uploadImage(image, userId, publicationId)
    }

    override suspend fun deleteImage(imagePath: String) {
        storageNetwork.deleteImageToStorage(imagePath)
    }
}
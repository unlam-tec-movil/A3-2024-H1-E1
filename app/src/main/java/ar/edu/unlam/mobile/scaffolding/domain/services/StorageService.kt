package ar.edu.unlam.mobile.scaffolding.domain.services

import android.graphics.Bitmap
import ar.edu.unlam.mobile.scaffolding.data.repository.StorageRepository
import ar.edu.unlam.mobile.scaffolding.domain.usecases.DeleteImage
import ar.edu.unlam.mobile.scaffolding.domain.usecases.UploadImage
import javax.inject.Inject

class StorageService @Inject constructor(
    private val storageRepository: StorageRepository,
) : UploadImage, DeleteImage {
    override suspend fun uploadImage(
        image: Bitmap,
        userId: String,
        publicationId: String,
    ): String {
        return storageRepository.uploadImage(image, userId, publicationId)
    }

    override suspend fun deleteImage(imagePath: String) {
        storageRepository.deleteImage(imagePath)
    }
}
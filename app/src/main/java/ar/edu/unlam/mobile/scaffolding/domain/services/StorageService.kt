package ar.edu.unlam.mobile.scaffolding.domain.services

import android.graphics.Bitmap
import ar.edu.unlam.mobile.scaffolding.data.repository.StorageRepository
import ar.edu.unlam.mobile.scaffolding.domain.models.ImageData
import ar.edu.unlam.mobile.scaffolding.domain.usecases.DeleteImage
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GetImagesForUser
import ar.edu.unlam.mobile.scaffolding.domain.usecases.UploadImage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StorageService
    @Inject
    constructor(
        private val storageRepository: StorageRepository,
    ) : GetImagesForUser, UploadImage, DeleteImage {
        override suspend fun getImagesForUser(userId: String): Flow<Map<String, List<ImageData>>> {
            return storageRepository.getImagesForUser(userId)
        }

        override suspend fun uploadImage(
            image: Bitmap,
            userId: String,
            publicationId: String,
        ) {
            storageRepository.uploadImage(image, userId, publicationId)
        }

        override suspend fun deleteImage(imagePath: String) {
            storageRepository.deleteImage(imagePath)
        }
    }

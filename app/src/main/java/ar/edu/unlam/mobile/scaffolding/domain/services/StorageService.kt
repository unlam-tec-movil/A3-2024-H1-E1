package ar.edu.unlam.mobile.scaffolding.domain.services

import android.graphics.Bitmap
import ar.edu.unlam.mobile.scaffolding.data.repository.StorageRepository
import ar.edu.unlam.mobile.scaffolding.domain.models.ImageData
import ar.edu.unlam.mobile.scaffolding.domain.usecases.DeleteImage
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GetAllImages
import ar.edu.unlam.mobile.scaffolding.domain.usecases.UploadImage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StorageService
    @Inject
    constructor(
        private val storageRepository: StorageRepository,
    ) : GetAllImages, UploadImage, DeleteImage {
        override suspend fun getAllImages(userId: String): Flow<List<ImageData>> {
            return storageRepository.getAllImages(userId)
        }

        override suspend fun uploadImage(
            image: Bitmap,
            userId: String,
        ) {
            storageRepository.uploadImage(image, userId)
        }

        override suspend fun deleteImage(imagePath: String) {
            storageRepository.deleteImage(imagePath)
        }
    }

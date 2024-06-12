package ar.edu.unlam.mobile.scaffolding.domain.services

import android.graphics.Bitmap
import ar.edu.unlam.mobile.scaffolding.data.repository.StorageRepositoryInterface
import ar.edu.unlam.mobile.scaffolding.domain.usecases.DeleteImage
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GetAllImagesForPublication
import ar.edu.unlam.mobile.scaffolding.domain.usecases.UploadImage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StorageService
    @Inject
    constructor(
        private val storageRepository: StorageRepositoryInterface,
    ) : UploadImage, DeleteImage, GetAllImagesForPublication {
        override suspend fun uploadImage(
            image: Bitmap,
            userId: String,
            publicationId: String,
        ): String {
            return storageRepository.uploadImage(image, userId, publicationId)
        }

        override suspend fun deletePublicationImages(
            idUser: String,
            idPublication: String,
        ) {
            storageRepository.deletePublicationImages(idUser, idPublication)
        }

        override suspend fun getAllImagesForPublication(
            idUser: String,
            idPublication: String,
        ): Flow<List<Bitmap>> {
            return storageRepository.getAllImagesForPublication(idUser, idPublication)
        }
    }

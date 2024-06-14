package ar.edu.unlam.mobile.scaffolding.domain.services

import android.graphics.Bitmap
import ar.edu.unlam.mobile.scaffolding.data.repository.StorageRepositoryInterface
import ar.edu.unlam.mobile.scaffolding.domain.usecases.DeleteImage
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GetAllImagesForPublication
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GetAllImagesFromUrl
import ar.edu.unlam.mobile.scaffolding.domain.usecases.UploadImage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StorageService
    @Inject
    constructor(
        private val storageRepository: StorageRepositoryInterface,
    ) : UploadImage,
        DeleteImage,
        GetAllImagesForPublication,
        GetAllImagesFromUrl {
        override suspend fun uploadImage(
            image: Bitmap,
            userId: String,
            publicationId: String,
        ): String = storageRepository.uploadImage(image, userId, publicationId)

        override suspend fun deletePublicationImages(
            idUser: String,
            idPublication: String,
        ) {
            storageRepository.deletePublicationImages(idUser, idPublication)
        }

        override suspend fun getAllImagesForPublication(
            idUser: String,
            idPublication: String,
        ): Flow<List<Bitmap>> = storageRepository.getAllImagesForPublication(idUser, idPublication)

        override suspend fun getAllImagesFromUrl(listImage: List<String>): Flow<List<Bitmap>> =
            storageRepository.getAllImagesForUrl(listImage)
    }

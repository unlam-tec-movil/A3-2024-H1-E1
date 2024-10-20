package ar.edu.unlam.mobile.scaffolding.data.repository

import android.graphics.Bitmap
import ar.edu.unlam.mobile.scaffolding.data.network.StorageNetworkInterface
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StorageRepository
    @Inject
    constructor(
        private val storageNetwork: StorageNetworkInterface,
    ) : StorageRepositoryInterface {
        override suspend fun uploadImage(
            image: Bitmap,
            userId: String,
            publicationId: String,
        ): String = storageNetwork.uploadImage(image, userId, publicationId)

        override suspend fun deletePublicationImages(
            idUser: String,
            idPublication: String,
        ) {
            storageNetwork.deletePublicationImages(idUser, idPublication)
        }

        override suspend fun getAllImagesForPublication(
            idUser: String,
            idPublication: String,
        ): Flow<List<Bitmap>> = storageNetwork.getAllImagesForPublication(idUser, idPublication)

        override suspend fun getAllImagesForUrl(listImages: List<String>): Flow<List<Bitmap>> =
            storageNetwork.getAllImagesUserUrl(listImages)
    }

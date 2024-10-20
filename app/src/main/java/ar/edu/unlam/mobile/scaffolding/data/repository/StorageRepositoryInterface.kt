package ar.edu.unlam.mobile.scaffolding.data.repository

import android.graphics.Bitmap
import kotlinx.coroutines.flow.Flow

interface StorageRepositoryInterface {
    suspend fun uploadImage(
        image: Bitmap,
        userId: String,
        publicationId: String,
    ): String

    suspend fun deletePublicationImages(
        idUser: String,
        idPublication: String,
    )

    suspend fun getAllImagesForPublication(
        idUser: String,
        idPublication: String,
    ): Flow<List<Bitmap>>

    suspend fun getAllImagesForUrl(listImages: List<String>): Flow<List<Bitmap>>
}

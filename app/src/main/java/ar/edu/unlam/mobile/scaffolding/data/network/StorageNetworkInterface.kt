package ar.edu.unlam.mobile.scaffolding.data.network

import android.graphics.Bitmap
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.Flow

interface StorageNetworkInterface {
    suspend fun getStorageReference(userId: String): StorageReference

    suspend fun getStorageReferenceFromUrl(url: String): StorageReference

    suspend fun deletePublicationImages(
        idUser: String,
        idPublication: String,
    )

    suspend fun uploadImage(
        image: Bitmap,
        userId: String,
        publicationId: String,
    ): String

    suspend fun getAllImagesForPublication(
        idUser: String,
        idPublication: String,
    ): Flow<List<Bitmap>>

    suspend fun getAllImagesUserUrl(listImage: List<String>): Flow<List<Bitmap>>
}

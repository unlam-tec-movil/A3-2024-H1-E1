package ar.edu.unlam.mobile.scaffolding.data.network

import android.graphics.Bitmap
import ar.edu.unlam.mobile.scaffolding.domain.models.ImageData
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.Flow

interface StorageNetworkInterface {
    suspend fun getStorageReference(userId: String): StorageReference

    suspend fun getStorageReferenceFromUrl(url: String): StorageReference

    suspend fun getAllImagesForUser(userId: String): Flow<Map<String, List<ImageData>>>

    suspend fun getImagesForPublication(
        userId: String,
        publicationId: String,
    ): Flow<List<ImageData>>

    suspend fun deleteImage(imagePath: String)

    suspend fun uploadImage(
        image: Bitmap,
        userId: String,
        publicationId: String,
    )
}

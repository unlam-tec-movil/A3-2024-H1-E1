package ar.edu.unlam.mobile.scaffolding.data.repository

import android.graphics.Bitmap
import ar.edu.unlam.mobile.scaffolding.domain.models.ImageData
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.Flow

interface StorageRepositoryInterface {
    suspend fun getStorageReference(userId: String): StorageReference

    suspend fun getAllImages(userId: String): Flow<List<ImageData>>

    suspend fun uploadImage(
        image: Bitmap,
        userId: String,
    )

    suspend fun deleteImage(imagePath: String)
}

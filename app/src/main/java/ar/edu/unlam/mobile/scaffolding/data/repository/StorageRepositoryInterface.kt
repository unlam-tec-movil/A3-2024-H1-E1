package ar.edu.unlam.mobile.scaffolding.data.repository

import android.graphics.Bitmap
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.Flow

interface StorageRepositoryInterface {
    suspend fun getStorageReference(userId: String): StorageReference

    suspend fun getAllImages(userId: String): Flow<List<Bitmap>>

    suspend fun uploadImage(
        image: Bitmap,
        userId: String,
    )

    suspend fun deleteImage(imageUrl: String)
}

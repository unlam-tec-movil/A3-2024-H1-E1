package ar.edu.unlam.mobile.scaffolding.data.network

import android.graphics.Bitmap
import com.google.firebase.storage.StorageReference

interface StorageNetworkInterface {
    suspend fun getStorageReference(userId: String): StorageReference

    suspend fun getStorageReferenceFromUrl(url: String): StorageReference

    suspend fun deleteImageToStorage(imageUrl: String)

    suspend fun uploadImage(
        image: Bitmap,
        userId: String,
        publicationId: String,
    ): String
}

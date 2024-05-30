package ar.edu.unlam.mobile.scaffolding.data.repository

import android.graphics.Bitmap

interface StorageRepositoryInterface {
    suspend fun uploadImage(
        image: Bitmap,
        userId: String,
        publicationId: String,
    ): String

    suspend fun deleteImage(imagePath: String)
}

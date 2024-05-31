package ar.edu.unlam.mobile.scaffolding.domain.usecases

import android.graphics.Bitmap

interface UploadImage {
    suspend fun uploadImage(
        image: Bitmap,
        userId: String,
        publicationId: String,
    ): String
}

package ar.edu.unlam.mobile.scaffolding.domain.usecases

import android.graphics.Bitmap
import kotlinx.coroutines.flow.Flow

interface GetAllImagesForPublication {
    suspend fun getAllImagesForPublication(
        idUser: String,
        idPublication: String,
    ): Flow<List<Bitmap>>
}

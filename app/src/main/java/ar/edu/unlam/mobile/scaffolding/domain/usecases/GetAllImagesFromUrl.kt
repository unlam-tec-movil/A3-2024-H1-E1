package ar.edu.unlam.mobile.scaffolding.domain.usecases

import android.graphics.Bitmap
import kotlinx.coroutines.flow.Flow

interface GetAllImagesFromUrl {
    suspend fun getAllImagesFromUrl(listImage: List<String>): Flow<List<Bitmap>>
}

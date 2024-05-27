package ar.edu.unlam.mobile.scaffolding.domain.usecases

import ar.edu.unlam.mobile.scaffolding.domain.models.ImageData
import kotlinx.coroutines.flow.Flow

interface GetAllImages {
    suspend fun getAllImages(userId: String): Flow<List<ImageData>>
}

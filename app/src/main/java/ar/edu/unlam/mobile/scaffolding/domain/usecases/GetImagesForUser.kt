package ar.edu.unlam.mobile.scaffolding.domain.usecases

import ar.edu.unlam.mobile.scaffolding.domain.models.ImageData
import kotlinx.coroutines.flow.Flow

interface GetImagesForUser {
    suspend fun getImagesForUser(userId: String): Flow<Map<String, List<ImageData>>>
}

package ar.edu.unlam.mobile.scaffolding.domain.usecases

import ar.edu.unlam.mobile.scaffolding.domain.models.SimplifiedPublicationMarker
import kotlinx.coroutines.flow.Flow

interface GetMarkersUseCase {
    suspend operator fun invoke(): Flow<List<SimplifiedPublicationMarker>>
}

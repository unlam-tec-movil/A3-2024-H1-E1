package ar.edu.unlam.mobile.scaffolding.domain.usecases

import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface GetMarkersUseCase {
    operator fun invoke(): Flow<List<LatLng>>
}

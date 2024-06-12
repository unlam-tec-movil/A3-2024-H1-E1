package ar.edu.unlam.mobile.scaffolding.domain.usecases

import ar.edu.unlam.mobile.scaffolding.domain.services.LocationService
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocationUseCase
    @Inject
    constructor(
        private val locationService: LocationService,
    ) {
        operator fun invoke(): Flow<LatLng?> = locationService.requestLocationUpdates()
    }

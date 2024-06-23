package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import ar.edu.unlam.mobile.scaffolding.domain.models.SimplifiedPublicationMarker
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@Composable
fun MapsComponent(
    modifier: Modifier = Modifier,
    markers: List<SimplifiedPublicationMarker>,
    cameraPositionState: CameraPositionState,
    isUserLocationEnabled: State<Boolean>,
    userMarker: LatLng? = null,
) {
    val uiSettings =
        MapUiSettings(
            zoomControlsEnabled = false,
        )

    val properties =
        MapProperties(
            mapType = MapType.NORMAL,
            isMyLocationEnabled = isUserLocationEnabled.value,
        )

    GoogleMap(
        modifier =
            modifier
                .fillMaxSize(),
        uiSettings = uiSettings,
        properties = properties,
        cameraPositionState = cameraPositionState,
    ) {
        markers.forEach { location ->
            Marker(state = MarkerState(position = location.locationCoordinates))
        }
        if (userMarker != null) {
            Marker(state = MarkerState(position = userMarker))
        }
    }
}

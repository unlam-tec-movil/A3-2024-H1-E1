package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile.scaffolding.domain.models.SimplifiedPublicationMarker
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState

@Composable
fun MapsComponent(
    modifier: Modifier = Modifier,
    markers: State<List<SimplifiedPublicationMarker>>,
    cameraPositionState: CameraPositionState,
    isUserLocationEnabled: State<Boolean>,
    userMarker: LatLng? = null,
    onMarkerClick: (SimplifiedPublicationMarker) -> Boolean = { false },
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
        markers.value.forEach { publication ->
            MarkerComposable(
                state = MarkerState(position = publication.locationCoordinates),
                onClick = { onMarkerClick(publication) },
            ) {
                val speciesIcon: String = publication.species.getEmoji()
                Text(
                    text = speciesIcon,
                    fontSize = 30.sp,
                )
            }
        }
        if (userMarker != null) {
            Marker(state = MarkerState(position = userMarker))
        }
    }
}

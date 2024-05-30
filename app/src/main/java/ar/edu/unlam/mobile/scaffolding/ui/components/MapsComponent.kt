package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings

@Composable
fun MapsComponent(
    modifier: Modifier = Modifier,
    markers: List<LatLng> = listOf(),
    userLocation: LatLng? = null,
) {
//    val mapController = rememberCameraPositionState()
    var uiSettings by remember {
        mutableStateOf(MapUiSettings(myLocationButtonEnabled = true, zoomControlsEnabled = false))
    }

    var properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL, isMyLocationEnabled = true))
    }

    GoogleMap(
        modifier =
            modifier
                .fillMaxSize(),
        uiSettings = uiSettings,
        properties = properties,
    )
}

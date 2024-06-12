package ar.edu.unlam.mobile.scaffolding.ui.screens.publicationsMap

import android.Manifest
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.core.utils.hasLocationPermission
import ar.edu.unlam.mobile.scaffolding.ui.components.MapsComponent
import ar.edu.unlam.mobile.scaffolding.ui.components.RationaleAlert
import ar.edu.unlam.mobile.scaffolding.ui.components.SearchBox
import ar.edu.unlam.mobile.scaffolding.ui.navigation.NavigationRoutes
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PublicationsMapScreen(
    modifier: Modifier = Modifier,
    controller: NavHostController,
    viewModel: PublicationsMapViewModel = hiltViewModel(),
) {
    val permissionState =
        rememberMultiplePermissionsState(
            permissions =
                listOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                ),
        )
    val viewState by viewModel.viewState.collectAsState()
    val context = LocalContext.current
    val cameraState = rememberCameraPositionState()
    val currentLocation by viewModel.currentLocation.collectAsState()
    val isUserLocationEnabled by viewModel.isUserLocationEnabled.collectAsState()
    val cameraCenterLocation by viewModel.cameraCenterLocation.collectAsState()

    LaunchedEffect(cameraCenterLocation) {
        if (permissionState.allPermissionsGranted) {
            viewModel.handle(PermissionEvent.Granted)
            viewModel.centerMapOnUserLocation()
            cameraCenterLocation?.let { cameraState.centerOnLocation(it) }
        }
    }

    Box(
        modifier =
            Modifier
                .fillMaxSize(),
    ) {
        MapsComponent(
            markers = emptyList(),
            cameraPositionState = cameraState,
            isUserLocationEnabled = viewModel.isUserLocationEnabled.collectAsState(),
        )
        with(viewState) {
            when (this) {
                is ViewState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is ViewState.Success -> {
                    LaunchedEffect(Unit) {
                        viewModel.centerMapOnUserLocation()
                        viewModel.setIsUserLocationEnabled(true)
                        cameraState.centerOnLocation(cameraCenterLocation ?: LatLng(0.0, 0.0))
                    }
                }
                is ViewState.ShouldShowRationale -> {
                    RationaleAlert(onDismiss = { }) {
                        permissionState.launchMultiplePermissionRequest()
                    }
                }
                is ViewState.RevokedPermissions -> {
                    Column(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text("We need permissions to use this app")
                        Button(
                            onClick = {
                                context.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS))
                            },
                            enabled = !context.hasLocationPermission(),
                        ) {
                            if (context.hasLocationPermission()) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(14.dp),
                                    color = Color.White,
                                )
                            } else {
                                Text("Settings")
                            }
                        }
                    }
                }
            }
        }
        Column(
            modifier =
                Modifier
                    .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SearchBox(onLeadingIconClick = {
                controller.navigate(NavigationRoutes.FilterScreen.route)
            })
            Spacer(
                modifier =
                    Modifier
                        .weight(1f),
            )
        }

        FloatingActionButton(
            onClick = {
                if (!permissionState.allPermissionsGranted) {
                    permissionState.launchMultiplePermissionRequest()
                }
                when {
                    permissionState.allPermissionsGranted -> {
                        viewModel.handle(PermissionEvent.Granted)
                        currentLocation.let {
                            viewModel.centerMapOnUserLocation()
                        }
                    }

                    permissionState.shouldShowRationale -> {
                        viewModel.handle(PermissionEvent.ShouldShowRationale)
                    }

                    !permissionState.allPermissionsGranted && !permissionState.shouldShowRationale -> {
                        viewModel.handle(PermissionEvent.Revoked)
                    }
                }
            },
            modifier =
                Modifier
                    .align(alignment = Alignment.BottomStart)
                    .padding(bottom = 16.dp, start = 16.dp)
                    .size(42.dp),
        ) {
            Icon(painter = painterResource(R.drawable.baseline_my_location_24), contentDescription = "Center map on user location")
        }
    }
}

private suspend fun CameraPositionState.centerOnLocation(location: LatLng) =
    animate(
        update =
            CameraUpdateFactory.newLatLngZoom(
                location,
                15f,
            ),
        durationMs = 1500,
    )

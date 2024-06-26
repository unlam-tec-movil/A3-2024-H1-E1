package ar.edu.unlam.mobile.scaffolding.ui.screens.publicationsMap

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.ui.components.LoadingComponent
import ar.edu.unlam.mobile.scaffolding.ui.components.MapsComponent
import ar.edu.unlam.mobile.scaffolding.ui.components.PublicationDetailsSheet
import ar.edu.unlam.mobile.scaffolding.ui.components.RationaleAlert
import ar.edu.unlam.mobile.scaffolding.ui.components.SearchBox
import ar.edu.unlam.mobile.scaffolding.ui.navigation.NavigationRoutes
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
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
    val showRationaleAlert by viewModel.showRationaleAlert.collectAsState()
    val selectedMarker by viewModel.selectedMarker.collectAsState()
    val publicationMarkers by viewModel.publicationMarkers.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val showBottomSheetState = remember { mutableStateOf(false) }
    var showBottomSheet by showBottomSheetState::value
    val didRequestPermission by viewModel.didRequestPermission.collectAsState()

    LaunchedEffect(cameraCenterLocation) {
        if (permissionState.allPermissionsGranted) {
            viewModel.handle(PermissionEvent.Granted)
            viewModel.centerMapOnUserLocation()
            cameraCenterLocation?.let { cameraState.centerOnLocation(it) }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.getMarkers()
        viewModel.getRequestPermissionFlag()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        MapsComponent(
            markers = viewModel.publicationMarkers.collectAsState(),
            cameraPositionState = cameraState,
            isUserLocationEnabled = viewModel.isUserLocationEnabled.collectAsState(),
            onMarkerClick = { publication ->
                viewModel.setSelectedMarker(publication)
                showBottomSheet = true
                true
            },
        )
        with(viewState) {
            when (this) {
                is ViewState.Idle -> {
                }
                is ViewState.Loading -> {
                    LoadingComponent()
                }

                is ViewState.Success -> {
                    LaunchedEffect(Unit) {
                        viewModel.centerMapOnUserLocation()
                        viewModel.setIsUserLocationEnabled(true)
                        cameraState.centerOnLocation(cameraCenterLocation ?: LatLng(0.0, 0.0))
                    }
                }

                is ViewState.ShouldShowRationale -> {
                    if (showRationaleAlert) {
                        RationaleAlert(
                            onDismiss = { viewModel.dismissRationaleAlert() },
                            onConfirm = { },
//                            confirmButtonText = "Conceder permisos",
                        )
                    }
                }

                is ViewState.RevokedPermissions -> {
                    if (showRationaleAlert) {
                        RationaleAlert(
                            onDismiss = { viewModel.dismissRationaleAlert() },
                            onConfirm = {
                                val intent =
                                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        data = Uri.fromParts("package", context.packageName, null)
                                    }
                                context.startActivity(intent)
                            },
                            confirmButtonText = "Abrir configuración",
                        )
                    }
                }
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SearchBox(
                onLeadingIconClick = {
                    controller.navigate(NavigationRoutes.FilterScreen.route)
                },
                onTrailingIconClick = {
                    controller.navigate(NavigationRoutes.ProfileScreen.route)
                },
                listForSearch = viewModel.publicationsListState.collectAsState(),
                filterList = { query ->
                    viewModel.filterPublications(query)
                },
                controller = controller,
            )
            Spacer(
                modifier = Modifier.weight(1f),
            )
        }

        FloatingActionButton(
            onClick = {
                scope.launch {
                    Log.i("PublicationMapScreen", "permissionState: $permissionState")

                    if (!didRequestPermission && !permissionState.allPermissionsGranted) {
                        permissionState.launchMultiplePermissionRequest()
                        viewModel.setRequestPermissionFlag(true)
                    } else {
                        if (!permissionState.allPermissionsGranted) {
                            if (permissionState.shouldShowRationale) {
                                viewModel.handle(PermissionEvent.ShouldShowRationale)
                            } else {
                                viewModel.handle(PermissionEvent.Revoked)
                            }
                            permissionState.launchMultiplePermissionRequest()
                        } else {
                            viewModel.handle(PermissionEvent.Granted)
                            currentLocation.let {
                                viewModel.centerMapOnUserLocation()
                            }
                        }
                    }
                }
            },
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.primary,
            modifier =
                Modifier
                    .align(alignment = Alignment.BottomStart)
                    .padding(bottom = 16.dp, start = 16.dp)
                    .size(42.dp),
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_my_location_24),
                contentDescription = "Center map on user location",
            )
        }
    }
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState,
        ) {
            // Sheet content
            selectedMarker?.let {
                PublicationDetailsSheet(publication = it, primaryButtonOnClick = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                            controller.navigate(
                                NavigationRoutes.PublicationDetailsScreen.withPublicationId(
                                    selectedMarker?.id?.toString()
                                        ?: "0",
                                ),
                            )
                        }
                    }
                })
            }
        }
    }
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState,
        ) {
            // Sheet content
            selectedMarker?.let {
                PublicationDetailsSheet(publication = it, primaryButtonOnClick = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                            controller.navigate(
                                NavigationRoutes.PublicationDetailsScreen.withPublicationId(
                                    selectedMarker?.id?.toString()
                                        ?: "0",
                                ),
                            )
                        }
                    }
                })
            }
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

package ar.edu.unlam.mobile.scaffolding.ui.screens.publicationsMap

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.models.SimplifiedPublicationMarker
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GetLocationUseCase
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GetMarkersUseCase
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PublicationsMapViewModel
    @Inject
    constructor(
        private val getLocationUseCase: GetLocationUseCase,
        private val getMarkersUseCase: GetMarkersUseCase,
    ) : ViewModel() {
        private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Loading)
        val viewState = _viewState.asStateFlow()
        private val _isUserLocationEnabled = MutableStateFlow(false)
        val isUserLocationEnabled = _isUserLocationEnabled.asStateFlow()
        private var _currentLocation = MutableStateFlow<LatLng?>(null)
        var currentLocation = _currentLocation.asStateFlow()
        private val _showRationaleAlert = MutableStateFlow(false)
        val showRationaleAlert = _showRationaleAlert.asStateFlow()
        private var _cameraCenterLocation = MutableStateFlow<LatLng?>(null)
        val cameraCenterLocation = _cameraCenterLocation.asStateFlow()

        private val _markers = MutableStateFlow<List<LatLng>>(emptyList())
        val markers = _markers.asStateFlow()

        private val _publicationMarkers = MutableStateFlow<List<SimplifiedPublicationMarker>>(emptyList())
        val publicationMarkers = _publicationMarkers.asStateFlow()

        fun handle(event: PermissionEvent) {
            when (event) {
                is PermissionEvent.Granted -> {
                    viewModelScope.launch {
                        getLocationUseCase.invoke().collect { location ->
                            _viewState.value = ViewState.Success(location)
                            _isUserLocationEnabled.value = true
                            _currentLocation.value = location
                        }
                    }
                }

                is PermissionEvent.Revoked -> {
                    _showRationaleAlert.value = true
                    _viewState.value = ViewState.RevokedPermissions
                }

                is PermissionEvent.ShouldShowRationale -> {
                    _showRationaleAlert.value = true
                    _viewState.value = ViewState.ShouldShowRationale
                }
            }
        }

        fun getMarkers() {
            viewModelScope.launch {
                getMarkersUseCase.invoke().collect { publicationMarkers ->
                    publicationMarkers.forEach { publicationMarker ->
                        Log.d("PublicationMarker", publicationMarker.toString())
                    }
                    _publicationMarkers.value = publicationMarkers
                }
            }
        }

        fun centerMapOnUserLocation() {
            _currentLocation.value?.let {
                _cameraCenterLocation.value = it
            }
        }

        fun setIsUserLocationEnabled(isEnabled: Boolean) {
            _isUserLocationEnabled.value = isEnabled
        }

        fun dismissRationaleAlert() {
            _showRationaleAlert.value = false
        }
    }

sealed interface ViewState {
    object Loading : ViewState

    object RevokedPermissions : ViewState

    object ShouldShowRationale : ViewState

    data class Success(val location: LatLng?) : ViewState
}

sealed interface PermissionEvent {
    object Granted : PermissionEvent

    object Revoked : PermissionEvent

    object ShouldShowRationale : PermissionEvent
}

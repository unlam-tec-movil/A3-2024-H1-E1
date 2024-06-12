package ar.edu.unlam.mobile.scaffolding.ui.screens.publicationsMap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GetLocationUseCase
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
    ) : ViewModel() {
        private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Loading)
        val viewState = _viewState.asStateFlow()
        private val _isUserLocationEnabled = MutableStateFlow(false)
        val isUserLocationEnabled = _isUserLocationEnabled.asStateFlow()
        private var _currentLocation = MutableStateFlow<LatLng?>(null)
        var currentLocation = _currentLocation.asStateFlow()

        private var _cameraCenterLocation = MutableStateFlow<LatLng?>(null)
        val cameraCenterLocation = _cameraCenterLocation.asStateFlow()

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
                    _viewState.value = ViewState.RevokedPermissions
                }

                is PermissionEvent.ShouldShowRationale -> {
                    _viewState.value = ViewState.ShouldShowRationale
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

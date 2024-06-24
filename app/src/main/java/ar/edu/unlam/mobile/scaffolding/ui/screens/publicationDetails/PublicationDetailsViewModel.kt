package ar.edu.unlam.mobile.scaffolding.ui.screens.publicationDetails

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ar.edu.unlam.mobile.scaffolding.domain.models.PostWithImages
import ar.edu.unlam.mobile.scaffolding.domain.services.FirestoreService
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GetAllImagesFromUrl
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GetGyroscopeDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@Immutable
sealed interface PublicationState {
    data object Success : PublicationState

    data object Error : PublicationState

    data object Loading : PublicationState
}

data class PublicationUiState(
    val publicationState: PublicationState = PublicationState.Loading,
)

@HiltViewModel
class
PublicationDetailsViewModel
    @Inject
    constructor(
        private val firestoreService: FirestoreService,
        private val getAllImagesFromUrl: GetAllImagesFromUrl,
        private val getGyroscopeDataUseCase: GetGyroscopeDataUseCase,
    ) : ViewModel() {
        @Suppress("ktlint:standard:backing-property-naming")
        private val _publication = mutableStateOf<PostWithImages?>(null)

        private val _images = mutableStateOf<List<Bitmap>>(emptyList())
        val images: State<List<Bitmap>> = _images

        @Suppress("ktlint:standard:backing-property-naming")
        private val _publicationState = MutableStateFlow(PublicationState.Loading)

        private val _uiState = MutableStateFlow(PublicationUiState(_publicationState.value))
        val uiState: StateFlow<PublicationUiState> = _uiState.asStateFlow()

        val rotationY: StateFlow<Float> = getGyroscopeDataUseCase.rotationY

        suspend fun getPublicationById(publicationId: String): PostWithImages? =
            try {
                firestoreService.getPublicationById(publicationId).collect { publication ->
                    try {
                        getAllImagesFromUrl.getAllImagesFromUrl(publication.images).collect {
                            _images.value = it
                        }
                    } catch (e: Exception) {
                        Log.e("Error PDS", e.toString())
                    }
                    _publication.value = publication
                    _uiState.value = PublicationUiState(PublicationState.Success)
                }
                _publication.value
            } catch (e: Exception) {
                null
            }
    }

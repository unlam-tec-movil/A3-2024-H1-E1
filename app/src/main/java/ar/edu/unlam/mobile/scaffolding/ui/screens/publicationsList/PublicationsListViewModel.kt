package ar.edu.unlam.mobile.scaffolding.ui.screens.publicationsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.models.PublicationCellModel
import ar.edu.unlam.mobile.scaffolding.domain.services.FirestoreService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@Immutable
sealed interface PublicationsState {
    data object Success : PublicationsState

    data object Error : PublicationsState

    data object Loading : PublicationsState
}

data class PublicationsUiState(val publicationsState: PublicationsState = PublicationsState.Loading)

@HiltViewModel
class PublicationsListViewModel
    @Inject
    constructor(private val firestoreService: FirestoreService) : ViewModel() {
        @Suppress("ktlint:standard:backing-property-naming")
        private val _publications = MutableStateFlow<List<PublicationCellModel>>(emptyList())
        val publications: StateFlow<List<PublicationCellModel>> get() = _publications

        @Suppress("ktlint:standard:backing-property-naming")
        private val _publicationsState = MutableStateFlow(PublicationsState.Loading)

        private val _uiState = MutableStateFlow(PublicationsUiState(_publicationsState.value))
        val uiState: StateFlow<PublicationsUiState> = _uiState.asStateFlow()

        init {
            getPublications()
        }

        private fun getPublications() {
            viewModelScope.launch {
                firestoreService.getAllPublications().map { postWhitImageList ->
                    postWhitImageList.map {
                        PublicationCellModel(
                            id = it.id,
                            title = it.title,
                            description = it.description,
                            distance = "",
                            imageResourceId = (it.images.firstOrNull() ?: ""),
                            publicationType = it.type,
                        )
                    }
                }
                    .catch { e ->
                        // error
                    }
                    .collect { publicationCellModelsList ->
                        _publications.value = publicationCellModelsList
                        _uiState.value = PublicationsUiState(PublicationsState.Success)
                    }
            }
        }
    }

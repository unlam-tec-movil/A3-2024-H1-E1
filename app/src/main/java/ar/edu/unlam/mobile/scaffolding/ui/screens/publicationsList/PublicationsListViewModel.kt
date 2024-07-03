package ar.edu.unlam.mobile.scaffolding.ui.screens.publicationsList

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.models.FilterSettings
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

data class PublicationsUiState(
    val publicationsState: PublicationsState = PublicationsState.Loading,
)

@HiltViewModel
class PublicationsListViewModel
    @Inject
    constructor(
        private val firestoreService: FirestoreService,
    ) : ViewModel() {
        private val _publications = mutableStateOf<List<PublicationCellModel>>(emptyList())
        val publications: State<List<PublicationCellModel>> = _publications

        private val _publicationFilter = mutableStateOf<List<PublicationCellModel>>(emptyList())
        val publicationFilter: State<List<PublicationCellModel>> = _publicationFilter

        @Suppress("ktlint:standard:backing-property-naming")
        private val _publicationsState = MutableStateFlow(PublicationsState.Loading)

        private val _uiState = MutableStateFlow(PublicationsUiState(_publicationsState.value))
        val uiState: StateFlow<PublicationsUiState> = _uiState.asStateFlow()

        init {
            // nuestra publicacion debe obtener todas las
            getPublications()
        }

        private fun getPublications() {
            viewModelScope.launch {
                firestoreService
                    .getAllPublications()
                    .map { postWithImageList ->
                        postWithImageList.map {
                            PublicationCellModel(
                                id = it.id,
                                title = it.title,
                                description = it.description,
                                distance = "",
                                imageResourceId = (it.images.firstOrNull() ?: ""),
                                publicationType = it.type,
                                species = it.species,
                                dateLost = it.dateLost,
                            )
                        }
                    }.catch {
                        _uiState.value = PublicationsUiState(PublicationsState.Error)
                        // error
                    }.collect { publicationCellModelsList ->
                        _publications.value = publicationCellModelsList
                        _uiState.value = PublicationsUiState(PublicationsState.Success)
                        applyCurrentFilters()
                    }
            }
        }

        fun filterPublicationByTittle(query: String) {
            _publicationFilter.value =
                _publications.value.filter { it.title.contains(query, ignoreCase = true) }
//            Log.d("PublicationsListViewModel", "Filtered by title: ${_publicationFilter.value}")
        }

        fun applyFilters(filters: FilterSettings) {
            _publicationFilter.value =
                _publications.value.filter { publication ->
                    val speciesMatch =
                        filters.selectedSpecies.isEmpty() || publication.species == filters.selectedSpecies

                    @Suppress("ktlint:standard:max-line-length")
                    val typeMatch =
                        filters.selectedPublicationType.isEmpty() || publication.publicationType == filters.selectedPublicationType

                    @Suppress("ktlint:standard:max-line-length")
                    val distanceMatch =
                        filters.selectedDistance == 0f || publication.distance.toFloatOrNull() ?: 0f <= filters.selectedDistance
                    val dateMatch =
                        filters.selectedDateLost.isEmpty() || publication.dateLost == filters.selectedDateLost

//                    Log.d("PublicationsListViewModel", "Checking publication: $publication")
//                    Log.d("PublicationsListViewModel", "Species match: $speciesMatch")
//                    Log.d("PublicationsListViewModel", "Type match: $typeMatch")
//                    Log.d("PublicationsListViewModel", "Distance match: $distanceMatch")
//                    Log.d("PublicationsListViewModel", "Date match: $dateMatch")

                    speciesMatch && typeMatch && distanceMatch && dateMatch
                }

//            Log.d("PublicationsListViewModel", "Filtered publications: ${_publicationFilter.value}")
        }

        private fun applyCurrentFilters() {
            val currentFilters =
                FilterSettings(
                    selectedSpecies = "",
                    selectedPublicationType = "",
                    selectedDistance = 0f,
                    selectedDateLost = "",
                )
            applyFilters(currentFilters)
        }
    }

package ar.edu.unlam.mobile.scaffolding.ui.screens.publicationsList

import android.media.Image
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.models.PublicationCellModel
import ar.edu.unlam.mobile.scaffolding.domain.services.FirestoreService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@Immutable
sealed interface ImageState {
    data class Success(
        val imageState: Int,
    ) : ImageState

    data class Error(
        val imageState: Image,
    ) : ImageState

    data object Loading : ImageState
}

data class ListUIState(
    val imageState: ImageState,
)

@HiltViewModel
class PublicationsListViewModel
    @Inject
    constructor(
        private val firestoreService: FirestoreService,
    ) : ViewModel() {
        @Suppress("ktlint:standard:backing-property-naming")
        private val _publications = mutableStateOf<List<PublicationCellModel>>(emptyList())
        val publications: State<List<PublicationCellModel>> = _publications

        @Suppress("ktlint:standard:backing-property-naming")
        private val _publicationFilter = mutableStateOf<List<PublicationCellModel>>(emptyList())
        val publicatioFilter: State<List<PublicationCellModel>> = _publicationFilter

        init {
            // /nuestra publicacion debe obtener todas las
            getPublications()
            _publicationFilter.value = _publications.value
        }

        private fun getPublications() {
            viewModelScope.launch {
                firestoreService
                    .getAllPublications()
                    .map { postWhitImageList ->
                        postWhitImageList.map {
                            PublicationCellModel(
                                id = it.id,
                                title = it.title,
                                description = it.description,
                                distance = "",
                                imageResourceId = (it.images.firstOrNull() ?: ""),
                                publicationType = it.type,
                                species = "",
                                dateLost = "",
                            )
                        }
                    }.catch { e ->
                        // error
                    }.collect { publicationCellModelsList ->
                        _publications.value = publicationCellModelsList
                    }
            }
        }

        // /el filtro siempre va a ser sobre toda las publicaciones
        fun filterPublicationByTittle(query: String) {
            // /el filter siempre me devuelve una nueva lista
            _publicationFilter.value = _publications.value.filter { it.title.contains(query) }
        }
    }

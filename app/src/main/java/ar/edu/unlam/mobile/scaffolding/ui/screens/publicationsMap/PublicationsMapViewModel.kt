package ar.edu.unlam.mobile.scaffolding.ui.screens.publicationsMap

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ar.edu.unlam.mobile.scaffolding.domain.models.PublicationCellModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PublicationsMapViewModel
    @Inject
    constructor() : ViewModel() {
        private val _publicationsListState = mutableStateOf<List<PublicationCellModel>>(emptyList())
        val publicationsListState: State<List<PublicationCellModel>> = _publicationsListState

        init {
            // Load initial data here or use a repository
            loadPublications()
        }

        private fun loadPublications() {
            // Load your data here, for example:
            // _publicationsListState.value = repository.getPublications()
        }

        fun filterPublications(query: String) {
            // Implement your filtering logic here, for example:
            // _publicationsListState.value = repository.filterPublications(query)
        }
    }

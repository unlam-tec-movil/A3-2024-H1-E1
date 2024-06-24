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

            loadPublications()
        }

        private fun loadPublications() {
        }

        fun filterPublications(query: String) {
        }
    }

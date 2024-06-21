package ar.edu.unlam.mobile.scaffolding.ui.screens.filterSettings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ar.edu.unlam.mobile.scaffolding.domain.models.PublicationCellModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FilterSettingsViewModel
    @Inject
    constructor() : ViewModel() {
        var selectedSpecies by mutableStateOf("")
        var selectedPublicationType by mutableStateOf("")
        var selectedDistance by mutableStateOf(0f)
        var selectedDateLost by mutableStateOf("")

        fun applyFilters(publications: List<PublicationCellModel>): List<PublicationCellModel> {
            return publications.filter { publication ->
                (selectedSpecies == "Todos" || publication.species == selectedSpecies) &&
                    (selectedPublicationType == "Todos" || publication.publicationType == selectedPublicationType) &&
                    (selectedDistance == 0f || publication.distance.toFloat() <= selectedDistance) &&
                    (selectedDateLost.isEmpty() || publication.dateLost == selectedDateLost)
            }
        }
    }

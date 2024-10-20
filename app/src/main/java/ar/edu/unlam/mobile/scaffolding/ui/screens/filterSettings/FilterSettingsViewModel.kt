package ar.edu.unlam.mobile.scaffolding.ui.screens.filterSettings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ar.edu.unlam.mobile.scaffolding.domain.models.FilterSettings
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

        fun getFilters(): FilterSettings {
            return FilterSettings(
                selectedSpecies = selectedSpecies,
                selectedPublicationType = selectedPublicationType,
                selectedDistance = selectedDistance,
                selectedDateLost = selectedDateLost,
            )
        }
    }

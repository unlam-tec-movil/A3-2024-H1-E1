package ar.edu.unlam.mobile.scaffolding.domain.models

data class FilterSettings(
    val selectedSpecies: String,
    val selectedPublicationType: String,
    val selectedDistance: Float,
    val selectedDateLost: String,
)

package ar.edu.unlam.mobile.scaffolding.domain.models

data class PublicationCellModel(
    val id: String,
    val title: String,
    val description: String,
    val distance: String,
    val imageResourceId: String,
    val publicationType: String,
    val species: String,
    val dateLost: String,
)

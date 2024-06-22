package ar.edu.unlam.mobile.scaffolding.domain.models

data class SimplifiedPublicationMarker(
    val id: String,
    val type: String,
    val title: String,
    val description: String,
    val dateLost: String,
    val species: String,
    val locationCoordinates: com.google.android.gms.maps.model.LatLng,
    val images: List<String>,
)

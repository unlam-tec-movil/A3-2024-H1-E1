package ar.edu.unlam.mobile.scaffolding.domain.models

import com.google.android.gms.maps.model.LatLng

data class SimplifiedPublicationMarker(
    val id: String,
    val type: String,
    val title: String,
    val description: String,
    val dateLost: String,
    val species: Species,
    val locationCoordinates: LatLng,
    val images: List<String>,
)

package ar.edu.unlam.mobile.scaffolding.domain.models

class PostWithImages(
    val id: String,
    val type: String,
    val title: String,
    val description: String,
    val dateLost: String,
    val species: String,
    val sex: String,
    val age: Int,
    val color: String,
    val location: String,
    val contact: Int,
    val images: List<String>,
    val locationLat: Double,
    val locationLng: Double,
)

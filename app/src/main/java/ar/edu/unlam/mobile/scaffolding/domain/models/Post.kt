package ar.edu.unlam.mobile.scaffolding.domain.models

import java.util.Date

class Post(
    val title: String,
    val description: String,
    val dateLost: Date,
    val species: Species,
    val sex: Sex,
    val age: Int,
    val color: PetColors,
    val location: String,
    val contact: Int,
    val images: List<String>,
)

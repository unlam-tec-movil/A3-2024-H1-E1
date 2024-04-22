package ar.edu.unlam.mobile.scaffolding.domain.models

import java.util.Date

class Post(
    val title: String,
    val description:String,
    val dateLost:Date,
    val species: SPECIES,
    val sex: SEXO,
    val age:Int,
    val color:COLOR,
    val location:String,
    val contact:Int,
    val images:List<String>
)
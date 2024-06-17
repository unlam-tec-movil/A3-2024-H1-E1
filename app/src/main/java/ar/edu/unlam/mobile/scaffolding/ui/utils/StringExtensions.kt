package ar.edu.unlam.mobile.scaffolding.ui.utils

fun String.capitalizeFirstLetter(): String {
    return this.lowercase().replaceFirstChar {
        if (it.isLowerCase()) it.titlecase() else it.toString()
    }
}

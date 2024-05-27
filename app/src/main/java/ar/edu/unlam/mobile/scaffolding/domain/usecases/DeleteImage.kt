package ar.edu.unlam.mobile.scaffolding.domain.usecases

interface DeleteImage {
    suspend fun deleteImage(imagePath: String)
}

package ar.edu.unlam.mobile.scaffolding.domain.usecases

interface DeleteImage {
    suspend fun deletePublicationImages(
        idUser: String,
        idPublication: String,
    )
}

package ar.edu.unlam.mobile.scaffolding.domain.usecases

import ar.edu.unlam.mobile.scaffolding.domain.models.PostWithImages
import kotlinx.coroutines.flow.Flow

interface UseFirestore {
    suspend fun addPublication(
        idUser: String,
        publication: PostWithImages,
    ): Flow<PostWithImages>

    suspend fun addPublicationToPublicationCollection(publication: PostWithImages): Flow<PostWithImages>

    suspend fun getPublicationsByUserId(idUser: String): Flow<List<PostWithImages>>

    suspend fun getPublicationById(idPublication: String): Flow<PostWithImages>

    suspend fun getAllPublications(): Flow<List<PostWithImages>>

    suspend fun editPublicationForUser(
        idUser: String,
        idPublication: String,
        updatedPublication: PostWithImages,
    ): Flow<PostWithImages>

    suspend fun editPublicationInAllPublications(
        idPublication: String,
        updatedPublication: PostWithImages,
    ): Flow<PostWithImages>

    suspend fun deletePublicationForUser(
        userId: String,
        publicationId: String,
    ): Flow<Boolean>
}

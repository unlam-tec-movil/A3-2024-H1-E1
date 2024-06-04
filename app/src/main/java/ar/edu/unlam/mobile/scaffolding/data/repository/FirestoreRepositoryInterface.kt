package ar.edu.unlam.mobile.scaffolding.data.repository

import ar.edu.unlam.mobile.scaffolding.domain.models.PostWithImages
import kotlinx.coroutines.flow.Flow

interface FirestoreRepositoryInterface {
    suspend fun addPublication(
        idUser: String,
        publication: PostWithImages,
    ): Flow<PostWithImages>

    suspend fun addPublicationToPublicationCollection(publication: PostWithImages): Flow<PostWithImages>

    suspend fun getPublicationsByUserId(idUser: String): Flow<List<PostWithImages>>

    suspend fun getPublicationById(idPublication: String): Flow<PostWithImages>

    suspend fun getAllPublications(): Flow<List<PostWithImages>>
}

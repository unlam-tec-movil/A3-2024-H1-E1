package ar.edu.unlam.mobile.scaffolding.data.network

import ar.edu.unlam.mobile.scaffolding.domain.models.PostWithImages
import kotlinx.coroutines.flow.Flow

interface FirestoreNetworkInterface {
    suspend fun getAllPublications(): Flow<List<PostWithImages>>

    suspend fun getPublicationsForUser(idUser: String): Flow<List<PostWithImages>>

    suspend fun editPublication(
        idUser: String,
        idPubli: Int,
    ): Flow<PostWithImages>

    suspend fun addPublication(
        idUser: String,
        publication: PostWithImages,
    ): Flow<PostWithImages>

    suspend fun addPublicationToPublicationCollection(publication: PostWithImages): Flow<PostWithImages>
}

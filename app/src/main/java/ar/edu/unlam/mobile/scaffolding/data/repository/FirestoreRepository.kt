package ar.edu.unlam.mobile.scaffolding.data.repository

import ar.edu.unlam.mobile.scaffolding.data.network.FirestoreNetworkInterface
import ar.edu.unlam.mobile.scaffolding.domain.models.PostWithImages
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FirestoreRepository
    @Inject
    constructor(
        private val firestoreNetworkInterface: FirestoreNetworkInterface,
    ) {
        suspend fun addPublication(
            idUser: String,
            publication: PostWithImages,
        ): Flow<PostWithImages> {
            return firestoreNetworkInterface.addPublication(idUser, publication)
        }

        suspend fun addPublicationToPublicationCollection(publication: PostWithImages): Flow<PostWithImages> {
            return firestoreNetworkInterface.addPublicationToPublicationCollection(publication)
        }

        suspend fun getPublicationsByUserId(idUser: String): Flow<List<PostWithImages>> {
            return firestoreNetworkInterface.getPublicationsByUserId(idUser)
        }

        suspend fun getPublicationById(idPublication: String): Flow<PostWithImages> {
            return firestoreNetworkInterface.getPublicationById(idPublication)
        }

        suspend fun getAllPublications(): Flow<List<PostWithImages>> {
            return firestoreNetworkInterface.getAllPublications()
        }
    }

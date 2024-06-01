package ar.edu.unlam.mobile.scaffolding.domain.services

import ar.edu.unlam.mobile.scaffolding.data.network.FirestoreNetworkInterface
import ar.edu.unlam.mobile.scaffolding.domain.models.PostWithImages
import ar.edu.unlam.mobile.scaffolding.domain.usecases.UseFirestore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FirestoreService
    @Inject
    constructor(
        private val firestoreRepository: FirestoreNetworkInterface,
    ) : UseFirestore {
        override suspend fun addPublication(
            idUser: String,
            publication: PostWithImages,
        ): Flow<PostWithImages> {
            return firestoreRepository.addPublication(idUser, publication)
        }

        override suspend fun addPublicationToPublicationCollection(publication: PostWithImages): Flow<PostWithImages> {
            return firestoreRepository.addPublicationToPublicationCollection(publication)
        }

        override suspend fun getPublicationsForUser(idUser: String): Flow<List<PostWithImages>> {
            return firestoreRepository.getPublicationsForUser(idUser)
        }

        override suspend fun getPublicationForUserById(
            idUser: String,
            idPublication: String,
        ): Flow<PostWithImages> {
            return firestoreRepository.getPublicationForUserById(idUser, idPublication)
        }

        override suspend fun getAllPublications(): Flow<List<PostWithImages>> {
            return firestoreRepository.getAllPublications()
        }
    }

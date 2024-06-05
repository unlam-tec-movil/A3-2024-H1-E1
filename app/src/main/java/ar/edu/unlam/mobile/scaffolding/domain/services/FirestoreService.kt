package ar.edu.unlam.mobile.scaffolding.domain.services

import ar.edu.unlam.mobile.scaffolding.data.repository.FirestoreRepositoryInterface
import ar.edu.unlam.mobile.scaffolding.domain.models.PostWithImages
import ar.edu.unlam.mobile.scaffolding.domain.usecases.UseFirestore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// /deberia ir a buscar los datos al repositorio
class FirestoreService
    @Inject
    constructor(
        private val firestoreRepository: FirestoreRepositoryInterface,
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

        override suspend fun getPublicationsByUserId(idUser: String): Flow<List<PostWithImages>> {
            return firestoreRepository.getPublicationsByUserId(idUser)
        }

        override suspend fun getPublicationById(idPublication: String): Flow<PostWithImages> {
            return firestoreRepository.getPublicationById(idPublication)
        }

        override suspend fun getAllPublications(): Flow<List<PostWithImages>> {
            return firestoreRepository.getAllPublications()
        }

        override suspend fun editPublicationForUser(
            idUser: String,
            idPublication: String,
            updatedPublication: PostWithImages,
        ): Flow<PostWithImages> {
            return firestoreRepository.editPublicationForUser(idUser, idPublication, updatedPublication)
        }

        override suspend fun editPublicationInAllPublications(
            idPublication: String,
            updatedPublication: PostWithImages,
        ): Flow<PostWithImages> {
            return firestoreRepository.editPublicationInAllPublications(idPublication, updatedPublication)
        }
    }

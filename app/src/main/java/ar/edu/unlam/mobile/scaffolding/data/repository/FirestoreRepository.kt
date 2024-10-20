package ar.edu.unlam.mobile.scaffolding.data.repository

import ar.edu.unlam.mobile.scaffolding.data.network.FirestoreNetworkInterface
import ar.edu.unlam.mobile.scaffolding.domain.models.PostWithImages
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FirestoreRepository
    @Inject
    constructor(
        private val firestoreNetworkInterface: FirestoreNetworkInterface,
    ) : FirestoreRepositoryInterface {
        override suspend fun addPublication(
            idUser: String,
            publication: PostWithImages,
        ): Flow<PostWithImages> {
            return firestoreNetworkInterface.addPublication(idUser, publication)
        }

        override suspend fun addPublicationToPublicationCollection(publication: PostWithImages): Flow<PostWithImages> {
            return firestoreNetworkInterface.addPublicationToPublicationCollection(publication)
        }

        override suspend fun getPublicationsByUserId(idUser: String): Flow<List<PostWithImages>> {
            return firestoreNetworkInterface.getPublicationsByUserId(idUser)
        }

        override suspend fun getPublicationById(idPublication: String): Flow<PostWithImages> {
            return firestoreNetworkInterface.getPublicationById(idPublication)
        }

        override suspend fun getAllPublications(): Flow<List<PostWithImages>> {
            return firestoreNetworkInterface.getAllPublications()
        }

        override suspend fun deletePublicationForUser(
            idUser: String,
            idPublication: String,
        ): Flow<Boolean> {
            return firestoreNetworkInterface.deletePublicationForUser(idUser, idPublication)
        }

        override suspend fun deletePublicationInAllPublications(idPublication: String): Flow<Boolean> {
            return firestoreNetworkInterface.deletePublicationInAllPublications(idPublication)
        }

        override suspend fun editPublicationForUser(
            idUser: String,
            idPublication: String,
            updatedPublication: PostWithImages,
        ): Flow<PostWithImages> {
            return firestoreNetworkInterface.editPublicationForUser(idUser, idPublication, updatedPublication)
        }

        override suspend fun editPublicationInAllPublications(
            idPublication: String,
            updatedPublication: PostWithImages,
        ): Flow<PostWithImages> {
            return firestoreNetworkInterface.editPublicationInAllPublications(idPublication, updatedPublication)
        }
    }

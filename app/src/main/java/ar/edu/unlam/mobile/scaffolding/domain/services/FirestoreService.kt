package ar.edu.unlam.mobile.scaffolding.domain.services

import ar.edu.unlam.mobile.scaffolding.data.repository.FirestoreRepositoryInterface
import ar.edu.unlam.mobile.scaffolding.domain.models.PostWithImages
import ar.edu.unlam.mobile.scaffolding.domain.models.SimplifiedPublicationMarker
import ar.edu.unlam.mobile.scaffolding.domain.models.Species
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GetMarkersUseCase
import ar.edu.unlam.mobile.scaffolding.domain.usecases.UseFirestore
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// /deberia ir a buscar los datos al repositorio
class FirestoreService
    @Inject
    constructor(
        private val firestoreRepository: FirestoreRepositoryInterface,
    ) : UseFirestore, GetMarkersUseCase {
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
            return firestoreRepository.editPublicationInAllPublications(
                idPublication,
                updatedPublication,
            )
        }

        override suspend fun deletePublicationForUser(
            userId: String,
            publicationId: String,
        ): Flow<Boolean> {
            return firestoreRepository.deletePublicationForUser(userId, publicationId)
        }

        override suspend fun invoke(): Flow<List<SimplifiedPublicationMarker>> {
            return getAllPublications().map { posts ->
                posts.map { post ->
//                    val parts = post.location.split(", ")
//                    LatLng(parts[0].toDouble(), parts[1].toDouble())
                    SimplifiedPublicationMarker(
                        id = post.id,
                        type = post.type,
                        title = post.title,
                        description = post.description,
                        dateLost = post.dateLost,
                        species = Species.fromString(post.species),
                        locationCoordinates = LatLng(post.locationLat, post.locationLng),
                        images = post.images,
                    )
                }
            }
        }

        suspend fun deletePublicationInAllPublications(publicationId: String): Flow<Boolean> {
            return firestoreRepository.deletePublicationInAllPublications(publicationId)
        }

        private fun parseLatLng(location: String): LatLng? {
            // Assume location is in the format "latitude,longitude"
            val parts = location.split(",")
            return if (parts.size == 2) {
                val latitude = parts[0].toDoubleOrNull()
                val longitude = parts[1].toDoubleOrNull()
                if (latitude != null && longitude != null) {
                    LatLng(latitude, longitude)
                } else {
                    null
                }
            } else {
                null
            }
        }
    }

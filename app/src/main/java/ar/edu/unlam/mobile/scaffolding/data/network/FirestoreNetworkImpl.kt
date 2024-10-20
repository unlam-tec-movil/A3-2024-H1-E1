package ar.edu.unlam.mobile.scaffolding.data.network

import android.util.Log
import ar.edu.unlam.mobile.scaffolding.domain.models.PostWithImages
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreNetworkImpl
    @Inject
    constructor(
        private val firebaseFirestore: FirebaseFirestore,
    ) : FirestoreNetworkInterface {
        override suspend fun getAllPublications(): Flow<List<PostWithImages>> =
            flow {
                try {
                    val documentSnapshot =
                        firebaseFirestore
                            .collection("publications")
                            .document("AllPublications")
                            .get()
                            .await()

                    // Asegúrate de mapear los datos correctamente a tu modelo PostWithImages
                    val allPublications = documentSnapshot["AllPublications"] as? List<Map<String, Any>>
                    val publicationsList =
                        allPublications?.map { publicationData ->
                            PostWithImages(
                                id = publicationData["id"] as String,
                                type = publicationData["type"] as String,
                                title = publicationData["title"] as String,
                                description = publicationData["description"] as String,
                                dateLost = publicationData["dateLost"] as String,
                                species = publicationData["species"] as String,
                                sex = publicationData["sex"] as String,
                                age = (publicationData["age"] as Long).toInt(),
                                color = publicationData["color"] as String,
                                location = publicationData["location"] as String,
                                contact = (publicationData["contact"] as Long).toInt(),
                                images = (publicationData["images"] as List<String>),
                                locationLng = publicationData["locationLng"] as? Double ?: 0.0,
                                locationLat = publicationData["locationLat"] as? Double ?: 0.0,
                            )
                        } ?: emptyList()

                    emit(publicationsList)
                } catch (e: Exception) {
                    // Manejar excepciones como la inexistencia del documento del usuario o fallo en la actualización
                    throw e
                }
            }

        override suspend fun getPublicationsByUserId(idUser: String): Flow<List<PostWithImages>> =
            flow {
                try {
                    val documentSnapshot =
                        firebaseFirestore
                            .collection("users")
                            .document(idUser)
                            .get()
                            .await()

                    val publications = documentSnapshot["publications"] as? List<Map<String, Any>>
                    val postsWithImages =
                        publications?.map {
                            PostWithImages(
                                id = it["id"] as String,
                                type = it["type"] as String,
                                title = it["title"] as String,
                                description = it["description"] as String,
                                dateLost = it["dateLost"] as String,
                                species = it["species"] as String,
                                sex = it["sex"] as String,
                                age = (it["age"] as Long).toInt(),
                                color = it["color"] as String,
                                location = it["location"] as String,
                                contact = (it["contact"] as Long).toInt(),
                                images = (it["images"] as? List<String>) ?: emptyList(),
                                locationLng = it["locationLng"] as Double,
                                locationLat = it["locationLat"] as Double,
                            )
                        } ?: emptyList()

                    emit(postsWithImages)
                } catch (e: Exception) {
                    // Manejar excepciones como la inexistencia del documento del usuario o fallo en la actualización
                    throw e
                }
            }

        override suspend fun getPublicationById(idPublication: String): Flow<PostWithImages> =
            flow {
                try {
                    // Obtiene el documento de todas las publicaciones
                    val documentSnapshot =
                        firebaseFirestore
                            .collection("publications")
                            .document("AllPublications")
                            .get()
                            .await()

                    // Obtiene el array de publicaciones
                    val publications = documentSnapshot["AllPublications"] as? List<Map<String, Any>>

                    // Busca la publicación específica por id
                    val publication = publications?.find { it["id"] == idPublication }

                    // Mapea la publicación a PostWithImages si existe
                    val postWithImages =
                        publication?.let {
                            PostWithImages(
                                id = it["id"] as String,
                                type = it["type"] as String,
                                title = it["title"] as String,
                                description = it["description"] as String,
                                dateLost = it["dateLost"] as String,
                                species = it["species"] as String,
                                sex = it["sex"] as String,
                                age = (it["age"] as Long).toInt(),
                                color = it["color"] as String,
                                location = it["location"] as String,
                                contact = (it["contact"] as Long).toInt(),
                                images = (it["images"] as? List<String>) ?: emptyList(),
                                locationLat = it["locationLat"] as Double,
                                locationLng = it["locationLng"] as Double,
                            )
                        }

                    // Emite la publicación si se encuentra
                    if (postWithImages != null) {
                        emit(postWithImages)
                    }
                } catch (e: Exception) {
                    // Manejar excepciones como la inexistencia del documento del usuario o fallo en la actualización
                    throw e
                }
            }

        override suspend fun addPublication(
            idUser: String,
            publication: PostWithImages,
        ): Flow<PostWithImages> {
            val userDocRef = firebaseFirestore.collection("users").document(idUser)

            return flow {
                try {
                    // Recuperar el documento del usuario
                    val userDocument = userDocRef.get().await()
                    if (userDocument.exists()) {
                        // Añadir la publicación al campo de arreglo "publications"
                        userDocRef.update("publications", FieldValue.arrayUnion(publication)).await()
                    } else {
                        // Crear el documento del usuario con la publicación
                        val initialData = mapOf("publications" to listOf(publication))
                        userDocRef.set(initialData).await()
                    }
                    // Emitir la publicación añadida
                    emit(publication)
                } catch (e: Exception) {
                    // Manejar excepciones como la inexistencia del documento del usuario o fallo en la actualización
                    throw e
                }
            }
        }

        override suspend fun addPublicationToPublicationCollection(publication: PostWithImages): Flow<PostWithImages> {
            val userDocRef = firebaseFirestore.collection("publications").document("AllPublications")

            return flow {
                try {
                    val userDocument = userDocRef.get().await()
                    if (userDocument.exists()) {
                        // Añadir la publicación al campo de arreglo "AllPublications"
                        userDocRef.update("AllPublications", FieldValue.arrayUnion(publication)).await()
                    } else {
                        // Crear el documento AllPublications
                        val initialData = mapOf("AllPublications" to listOf(publication))
                        userDocRef.set(initialData).await()
                    }
                    // Emitir la publicación añadida
                    emit(publication)
                } catch (e: Exception) {
                    // Manejar excepciones como fallo en la actualización
                    throw e
                }
            }
        }

        override suspend fun editPublicationForUser(
            idUser: String,
            idPublication: String,
            updatedPublication: PostWithImages,
        ): Flow<PostWithImages> =
            flow {
                try {
                    // Obtiene el documento del usuario
                    val documentSnapshot =
                        firebaseFirestore
                            .collection("users")
                            .document(idUser)
                            .get()
                            .await()

                    // Obtiene el array de publicaciones
                    val publications = documentSnapshot["publications"] as? List<Map<String, Any>> ?: emptyList()

                    // Mapea las publicaciones y actualiza la que coincida con idPublication
                    val updatedPublications =
                        publications.map { publicationData ->
                            if (publicationData["id"] == idPublication) {
                                updatedPublication
                            } else {
                                PostWithImages(
                                    id = publicationData["id"] as String,
                                    type = publicationData["type"] as String,
                                    title = publicationData["title"] as String,
                                    description = publicationData["description"] as String,
                                    dateLost = publicationData["dateLost"] as String,
                                    species = publicationData["species"] as String,
                                    sex = publicationData["sex"] as String,
                                    age = (publicationData["age"] as Long).toInt(),
                                    color = publicationData["color"] as String,
                                    location = publicationData["location"] as String,
                                    contact = (publicationData["contact"] as Long).toInt(),
                                    images = (publicationData["images"] as List<String>),
                                    locationLat = publicationData["locationLat"] as Double,
                                    locationLng = publicationData["locationLng"] as Double,
                                )
                            }
                        }

                    // Actualiza el documento del usuario con la lista de publicaciones actualizada
                    firebaseFirestore
                        .collection("users")
                        .document(idUser)
                        .update("publications", updatedPublications)
                        .await()

                    // Emite la publicación actualizada
                    emit(updatedPublication)
                } catch (e: Exception) {
                    // Maneja excepciones y emite null en caso de error
                    throw e
                }
            }

        override suspend fun editPublicationInAllPublications(
            idPublication: String,
            updatedPublication: PostWithImages,
        ): Flow<PostWithImages> =
            flow {
                try {
                    // Obtiene el documento de todas las publicaciones
                    val documentSnapshot =
                        firebaseFirestore
                            .collection("publications")
                            .document("AllPublications")
                            .get()
                            .await()

                    // Obtiene el array de publicaciones
                    val allPublications = documentSnapshot["AllPublications"] as? List<Map<String, Any>> ?: emptyList()

                    // Mapea las publicaciones y actualiza la que coincida con idPublication
                    val updatedPublications =
                        allPublications.map { publicationData ->
                            if (publicationData["id"] == idPublication) {
                                updatedPublication
                            } else {
                                PostWithImages(
                                    id = publicationData["id"] as String,
                                    type = publicationData["type"] as String,
                                    title = publicationData["title"] as String,
                                    description = publicationData["description"] as String,
                                    dateLost = publicationData["dateLost"] as String,
                                    species = publicationData["species"] as String,
                                    sex = publicationData["sex"] as String,
                                    age = (publicationData["age"] as Long).toInt(),
                                    color = publicationData["color"] as String,
                                    location = publicationData["location"] as String,
                                    contact = (publicationData["contact"] as Long).toInt(),
                                    images = (publicationData["images"] as List<String>),
                                    locationLat = publicationData["locationLat"] as Double,
                                    locationLng = publicationData["locationLng"] as Double,
                                )
                            }
                        }

                    // Actualiza el documento de todas las publicaciones con la lista actualizada
                    firebaseFirestore
                        .collection("publications")
                        .document("AllPublications")
                        .update("AllPublications", updatedPublications)
                        .await()

                    // Emite la publicación actualizada
                    emit(updatedPublication)
                } catch (e: Exception) {
                    // Maneja excepciones y emite null en caso de error
                    throw e
                }
            }

        override suspend fun deletePublicationForUser(
            idUser: String,
            idPublication: String,
        ): Flow<Boolean> =
            flow {
                try {
                    // Obtiene el documento del usuario
                    val documentSnapshot =
                        firebaseFirestore
                            .collection("users")
                            .document(idUser)
                            .get()
                            .await()

                    // Obtiene el array de publicaciones
                    val publications = documentSnapshot["publications"] as? List<Map<String, Any>> ?: emptyList()

                    // Filtra las publicaciones para eliminar la que coincida con idPublication
                    val updatedPublications =
                        publications.filterNot { publicationData ->
                            val publicationId = publicationData["id"].toString()
                            Log.d("deletePublicationForUser", "Comparando: $publicationId con $idPublication")
                            publicationId == idPublication
                        }
                    if (publications.size == updatedPublications.size) {
                        Log.e("deletePublicationForUser", "La publicación con ID $idPublication no fue encontrada")
                        emit(false)
                        return@flow
                    }
                    // Actualiza el documento del usuario con la lista de publicaciones actualizada
                    firebaseFirestore
                        .collection("users")
                        .document(idUser)
                        .update("publications", updatedPublications)
                        .await()

                    // Emite true si la publicación fue eliminada
                    emit(true)
                } catch (e: Exception) {
                    // Maneja excepciones y emite false en caso de error
                    emit(false)
                }
            }

        override suspend fun deletePublicationInAllPublications(idPublication: String): Flow<Boolean> =
            flow {
                try {
                    // Obtiene el documento de todas las publicaciones
                    val documentSnapshot =
                        firebaseFirestore
                            .collection("publications")
                            .document("AllPublications")
                            .get()
                            .await()

                    // Obtiene el array de publicaciones
                    val allPublications = documentSnapshot["AllPublications"] as? List<Map<String, Any>> ?: emptyList()

                    // Filtra las publicaciones para eliminar la que coincida con idPublication
                    val updatedPublications =
                        allPublications.filterNot { publicationData ->
                            publicationData["id"].toString() == idPublication
                        }

                    // Actualiza el documento de todas las publicaciones con la lista actualizada
                    firebaseFirestore
                        .collection("publications")
                        .document("AllPublications")
                        .update("AllPublications", updatedPublications)
                        .await()

                    // Emite true si la publicación fue eliminada
                    emit(true)
                } catch (e: Exception) {
                    // Maneja excepciones y emite false en caso de error
                    emit(false)
                }
            }
    }

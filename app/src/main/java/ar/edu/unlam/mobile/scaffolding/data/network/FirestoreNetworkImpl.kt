package ar.edu.unlam.mobile.scaffolding.data.network

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
                        firebaseFirestore.collection("publications")
                            .document("AllPublications")
                            .get()
                            .await()

                    val allPublications = documentSnapshot["AllPublications"] as? List<PostWithImages>
                    allPublications?.let {
                        emit(it)
                    } ?: emit(emptyList())
                } catch (e: Exception) {
                    // Handle exceptions
                    emit(emptyList()) // Or emit an error state
                }
            }

        override suspend fun getPublicationsForUser(idUser: String): Flow<List<PostWithImages>> =
            flow {
                try {
                    val documentSnapshot =
                        firebaseFirestore.collection("users")
                            .document(idUser)
                            .get()
                            .await()

                    val publications = documentSnapshot["publications"] as? List<PostWithImages>
                    publications?.let {
                        emit(it)
                    } ?: emit(emptyList())
                } catch (e: Exception) {
                    // Handle exceptions
                    emit(emptyList()) // Or emit an error state
                }
            }

        override suspend fun editPublication(
            idUser: String,
            idPubli: Int,
        ): Flow<PostWithImages> {
            TODO("Not yet implemented")
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
    }

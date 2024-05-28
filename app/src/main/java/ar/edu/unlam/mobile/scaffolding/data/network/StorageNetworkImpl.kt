package ar.edu.unlam.mobile.scaffolding.data.network

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import ar.edu.unlam.mobile.scaffolding.domain.models.ImageData
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class StorageNetworkImpl
    @Inject
    constructor(
        private val storage: FirebaseStorage,
    ) : StorageNetworkInterface {
        override suspend fun getStorageReference(userId: String): StorageReference {
            return storage.getReference(userId)
        }

        override suspend fun getStorageReferenceFromUrl(url: String): StorageReference {
            return storage.getReferenceFromUrl(url)
        }

        override suspend fun getAllImagesForUser(userId: String): Flow<Map<String, List<ImageData>>> =
            flow {
                val userRef = getStorageReference(userId = userId)
                val publicationsMap = mutableMapOf<String, List<ImageData>>()

                try {
                    val allPublicationsRef =
                        userRef.listAll()
                            .await().prefixes // Obtener todas las carpetas de publicaciones
                    for (publicationRef in allPublicationsRef) {
                        val publicationId = publicationRef.name
                        val allImageRefs =
                            publicationRef.listAll()
                                .await().items // Obtener todas las imágenes dentro de cada publicación
                        val imageList = mutableListOf<ImageData>()

                        for (img in allImageRefs) {
                            val imageByte = img.getBytes(Long.MAX_VALUE).await()
                            val imagePath = img.path
                            val bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.size)
                            val imageData =
                                ImageData(
                                    imagePath = imagePath,
                                    image = bitmap,
                                )
                            imageList.add(imageData)
                        }
                        publicationsMap[publicationId] = imageList
                    }
                    emit(publicationsMap)
                } catch (e: Exception) {
                    throw e
                }
            }

        override suspend fun getImagesForPublication(
            userId: String,
            publicationId: String,
        ): Flow<List<ImageData>> =
            flow {
                val userRef = storage.getReference(userId)
                val publicationRef =
                    userRef.child(publicationId) // Obtener todas las carpetas de publicaciones
                val allImageRefs = publicationRef.listAll().await().items
                val imageList = mutableListOf<ImageData>()
                try {
                    // Obtener todas las imágenes dentro de cada publicación
                    for (img in allImageRefs) {
                        val imageByte = img.getBytes(Long.MAX_VALUE).await()
                        val imagePath = img.path
                        val bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.size)
                        val imageData =
                            ImageData(
                                imagePath = imagePath,
                                image = bitmap,
                            )
                        imageList.add(imageData)
                    }
                    emit(imageList)
                } catch (e: Exception) {
                    throw e
                }
            }

        override suspend fun uploadImage(
            image: Bitmap,
            userId: String,
            publicationId: String,
        ) {
            // userId//publicationId//image , esa es la ruta
            val storageRef = getStorageReference(userId)
            val publicationRef = storageRef.child(publicationId)
            val imgReference = publicationRef.child("$image")
            val baos = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val imageData = baos.toByteArray()
            try {
                imgReference.putBytes(imageData)
                Log.d("StorageService", "Image uploaded successfully:")
            } catch (e: Exception) {
                // /que hacemos si no pudo subir la img , x alguna razon
                Log.d("StorageService", "Image failed upload to firebase Storage")
                throw e
            }
        }

        override suspend fun deleteImage(imagePath: String) {
            try {
                val imageRef = getStorageReference(imagePath)
                imageRef.delete().await()
                Log.e("", "delete image successfully")
            } catch (e: Exception) {
                Log.e("", "image delete failed imagePath: $imagePath")
            }
        }
    }

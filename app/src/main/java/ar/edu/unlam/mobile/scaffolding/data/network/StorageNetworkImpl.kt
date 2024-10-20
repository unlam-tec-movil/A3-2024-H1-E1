package ar.edu.unlam.mobile.scaffolding.data.network

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
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
        override suspend fun getStorageReference(userId: String): StorageReference = storage.getReference(userId)

        override suspend fun getStorageReferenceFromUrl(url: String): StorageReference = storage.getReferenceFromUrl(url)

        override suspend fun uploadImage(
            image: Bitmap,
            userId: String,
            publicationId: String,
        ): String {
            // userId/publicationId/image.jpg , esa es la ruta
            val storageRef = getStorageReference(userId)
            val publicationRef = storageRef.child(publicationId)
            // /es necesario hacer un nuevo nombre
            val imgReference = publicationRef.child("image_${System.currentTimeMillis()}.jpg")
            val baos = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val imageData = baos.toByteArray()
            return try {
                imgReference.putBytes(imageData).await()
                val downloadUrl = imgReference.downloadUrl.await().toString()
                downloadUrl
            } catch (e: Exception) {
                // Qué hacemos si no pudo subir la imagen, por alguna razón
                Log.d("StorageService", "Image failed upload to firebase Storage", e)
                throw e
            }
        }

        override suspend fun getAllImagesForPublication(
            idUser: String,
            idPublication: String,
        ): Flow<List<Bitmap>> =
            flow {
                try {
                    val userRef = getStorageReference(userId = idUser)
                    val publicationRef = userRef.child(idPublication)
                    val listImages = mutableListOf<Bitmap>()
                    val listItem = publicationRef.listAll().await().items
                    for (item in listItem) {
                        val imageByte = item.getBytes(Long.MAX_VALUE).await()
                        val bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.size)
                        listImages.add(bitmap)
                    }
                    emit(listImages)
                } catch (e: Exception) {
                    Log.e("Storage", "Failed get images for publication")
                    throw e
                }
            }

        override suspend fun getAllImagesUserUrl(listImage: List<String>): Flow<List<Bitmap>> =
            flow {
                val listBitmap = mutableListOf<Bitmap>()
                try {
                    for (image in listImage) {
                        val imageRef = getStorageReferenceFromUrl(image)
                        val imageByte = imageRef.getBytes(Long.MAX_VALUE).await()
                        val bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.size)
                        listBitmap.add(bitmap)
                    }
                    emit(listBitmap)
                } catch (e: Exception) {
                    Log.e("image Storage", "get images from url failed")
                    emit(emptyList())
                }
            }

        override suspend fun deletePublicationImages(
            idUser: String,
            idPublication: String,
        ) {
            val storageRe = getStorageReference(userId = idUser)
            try {
                val publicationRef = storageRe.child(idPublication)
                val list = publicationRef.listAll().await() // obtenemos todas las listas
                for (item in list.items) {
                    try {
                        item.delete().await()
                        Log.e("deleteImage", "se elimino exitosamente las imagenes")
                    } catch (e: Exception) {
                        Log.e("deleteImage", "algo fallo al eliminar la imagen")
                    }
                }
                Log.e("", "delete image successfully")
            } catch (e: Exception) {
                Log.e("", "image delete failed")
            }
        }
    }

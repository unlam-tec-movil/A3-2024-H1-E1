package ar.edu.unlam.mobile.scaffolding.data.network

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
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

        override suspend fun uploadImage(
            image: Bitmap,
            userId: String,
            publicationId: String,
        ): String {
            // userId/publicationId/image.jpg , esa es la ruta
            val storageRef = getStorageReference(userId)
            val publicationRef = storageRef.child(publicationId)
            val imgReference = publicationRef.child("image_${System.currentTimeMillis()}.jpg")
            val baos = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val imageData = baos.toByteArray()
            return try {
                val uploadTask = imgReference.putBytes(imageData).await()
                val downloadUrl = imgReference.downloadUrl.await().toString()
                Log.d("StorageService", "Image uploaded successfully: $downloadUrl")
                downloadUrl
            } catch (e: Exception) {
                // Qué hacemos si no pudo subir la imagen, por alguna razón
                Log.d("StorageService", "Image failed upload to firebase Storage", e)
                throw e
            }
        }

        override suspend fun deleteImageToStorage(imageUrl: String) {
            try {
                val imageRef = getStorageReferenceFromUrl(imageUrl)
                imageRef.delete().await()
                Log.e("", "delete image successfully")
            } catch (e: Exception) {
                Log.e("", "image delete failed imagePath: $imageUrl")
            }
        }
    }

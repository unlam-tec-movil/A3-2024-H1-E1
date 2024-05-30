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
            // userId//publicationId//image , esa es la ruta
            val storageRef = getStorageReference(userId)
            val publicationRef = storageRef.child(publicationId)
            val imgReference = publicationRef.child("$image")
            val baos = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val imageData = baos.toByteArray()
            return try {
                imgReference.putBytes(imageData)
                Log.d("StorageService", "Image uploaded successfully:")
                imgReference.downloadUrl.await().toString()
            } catch (e: Exception) {
                // /que hacemos si no pudo subir la img , x alguna razon
                Log.d("StorageService", "Image failed upload to firebase Storage")
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

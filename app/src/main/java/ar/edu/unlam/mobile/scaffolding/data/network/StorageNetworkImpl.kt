package ar.edu.unlam.mobile.scaffolding.data.network

import android.graphics.Bitmap
import android.graphics.BitmapFactory
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

        override suspend fun getAllImages(userId: String): Flow<List<Bitmap>> =
            flow {
                val storageRef = getStorageReference(userId = userId)
                val imageList = mutableListOf<Bitmap>()
                try {
                    val allImageRef = storageRef.listAll().await().items
                    for (img in allImageRef) {
                        val imageData = img.getBytes(Long.MAX_VALUE).await()
                        val bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
                        imageList.add(bitmap)
                    }
                    emit(imageList)
                } catch (e: Exception) {
                    // en caso de que no pueda traer la lista tengo que mostrar una exception
                    throw e
                }
            }

        override suspend fun uploadImage(
            image: Bitmap,
            userId: String,
        ) {
            val storageRef = getStorageReference(userId)
            val imgRe = storageRef.child("$image")

            val baos = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val imageData = baos.toByteArray()
            try {
                imgRe.putBytes(imageData)
            } catch (e: Exception) {
                // /que hacemos si no pudo subir la img , x alguna razon
                throw e
            }
        }

        override suspend fun deleteImage(imageUrl: String) {
            TODO("Not yet implemented")
        }
    }

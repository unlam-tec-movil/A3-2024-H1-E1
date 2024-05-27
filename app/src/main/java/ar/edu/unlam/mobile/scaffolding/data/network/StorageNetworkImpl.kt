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

        override suspend fun getAllImages(userId: String): Flow<List<ImageData>> =
            flow {
                val storageRef = getStorageReference(userId = userId)
                val imageList = mutableListOf<ImageData>()
                try {
                    val allImageRef = storageRef.listAll().await().items
                    for (img in allImageRef) {
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
                    // en caso de que no pueda traer la lista tengo que mostrar una exception
                    throw e
                }
            }

        override suspend fun uploadImage(
            image: Bitmap,
            userId: String,
        ) {
            val storageRef = getStorageReference(userId)
            val imaRef = storageRef.child("$image")
            val baos = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val imageData = baos.toByteArray()
            try {
                imaRef.putBytes(imageData)
            } catch (e: Exception) {
                // /que hacemos si no pudo subir la img , x alguna razon
                throw e
            }
        }

        override suspend fun deleteImage(imagePath: String) {
            try {
                val imageRef = getStorageReference(imagePath)
                imageRef.delete().await()
                Log.e("", "delete image successfully")
            } catch (e: Exception) {
                Log.e("", "image delete failed imagePaht: $imagePath")
            }
        }
    }

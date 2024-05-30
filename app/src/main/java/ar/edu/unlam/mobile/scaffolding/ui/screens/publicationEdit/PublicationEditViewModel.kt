package ar.edu.unlam.mobile.scaffolding.ui.screens.publicationEdit

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.models.UserInfoGoogle
import ar.edu.unlam.mobile.scaffolding.domain.services.StorageService
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GetCurrentUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PublicationEditViewModel
    @Inject
    constructor(
        private val storageService: StorageService,
        private val getUser: GetCurrentUser,
        context: Context,
    ) : ViewModel() {
        private var currentUserId: UserInfoGoogle? = null

        @Suppress("ktlint:standard:backing-property-naming")
        private val _listImagesForUser =
            mutableStateOf<List<Bitmap>>(emptyList())

        // Exponemos _listImagesForUser como State
        val listImageForPublication: State<List<Bitmap>> = _listImagesForUser

        init {
            viewModelScope.launch {
                currentUserId = getCurrentUser()
            }
        }

        fun addImage(imageBitmap: Bitmap) {
            // en esta funcion agregamos las imagenes a la lista de imagenes
            _listImagesForUser.value += imageBitmap
        }

        // elimina una imagen
        fun deleteImage(imageBitmap: Bitmap) {
            _listImagesForUser.value = _listImagesForUser.value.filterNot { it == imageBitmap }
        }

        private suspend fun getCurrentUser(): UserInfoGoogle? {
            val firebaseUser = getUser.getCurrentUser()
            return if (firebaseUser != null) {
                UserInfoGoogle(
                    userId = firebaseUser.uid,
                    displayName = firebaseUser.displayName,
                    email = firebaseUser.email,
                    photoUrl = firebaseUser.photoUrl.toString(),
                )
            } else {
                null
            }
        }

        fun hasRequirePermission(
            permisssion: Array<String>,
            context: Context,
        ): Boolean {
            return permisssion.all {
                ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
            }
        }

        fun createFile(context: Context): String? {
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
            val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val file = File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
            return file.absolutePath
        }

        fun captureImage(
            context: Context,
            cameraLauncher: ActivityResultLauncher<Intent>,
            file: File,
        ) {
            val photoUri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
            val cameraIntent =
                Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                    putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                }
            cameraLauncher.launch(cameraIntent)
        }

        fun resetListOfImages() {
            _listImagesForUser.value = emptyList()
        }
    }

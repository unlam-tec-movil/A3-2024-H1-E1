package ar.edu.unlam.mobile.scaffolding.ui.screens.publicationEdit

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.models.ImageData
import ar.edu.unlam.mobile.scaffolding.domain.models.UserInfoGoogle
import ar.edu.unlam.mobile.scaffolding.domain.services.StorageService
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GetCurrentUser
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
        private val signInClient = Identity.getSignInClient(context)
        private var currentUserId: UserInfoGoogle? = null

        @Suppress("ktlint:standard:backing-property-naming")
        private val _listImagesForUser: MutableStateFlow<List<ImageData>> = MutableStateFlow(emptyList())
        val listImagesUser: StateFlow<List<ImageData>> = _listImagesForUser

        init {
            viewModelScope.launch {
                currentUserId = getCurrentUser()
            }
            getAllImages()
        }

        private suspend fun getCurrentUser(): UserInfoGoogle? {
            val firebaseUser = getUser.getCurrentUser()
            if (firebaseUser != null) {
                return UserInfoGoogle(
                    userId = firebaseUser.uid,
                    displayName = firebaseUser.displayName,
                    email = firebaseUser.email,
                    photoUrl = firebaseUser.photoUrl.toString(),
                )
            } else {
                return null
            }
        }

        fun hasRequirePermission(
            camerXPermisssion: Array<String>,
            context: Context,
        ): Boolean {
            return camerXPermisssion.all {
                ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
            }
        }

        fun uploadImage(image: Bitmap) {
            // llamamos al service
            viewModelScope.launch {
                storageService.uploadImage(image, currentUserId?.userId.toString())
            }
        }

        fun getAllImages() {
            viewModelScope.launch {
                storageService.getAllImages(currentUserId?.userId.toString()).collect { images ->
                    _listImagesForUser.value = images
                }
            }
        }

        fun deleteImage(imagePath: String) {
            viewModelScope.launch {
                storageService.deleteImage(imagePath)
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
    }

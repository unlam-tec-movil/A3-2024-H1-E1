package ar.edu.unlam.mobile.scaffolding.ui.screens.publicationDetails

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.models.PostWithImages
import ar.edu.unlam.mobile.scaffolding.domain.services.FirestoreService
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GetAllImagesForPublication
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GetCurrentUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class
PublicationDetailsViewModel
    @Inject
    constructor(
        private val firestoreService: FirestoreService,
        private val currentUser: GetCurrentUser,
        private val getAllImagesForPublication: GetAllImagesForPublication,
    ) :
    ViewModel() {
        @Suppress("ktlint:standard:backing-property-naming")
        private val _publication = mutableStateOf<PostWithImages?>(null)
        private val _images = mutableStateOf<List<Bitmap>>(emptyList())
        val images: State<List<Bitmap>> = _images

        private var currentUserId: String? = null

        init {
            viewModelScope.launch {
                currentUserId = getCurrentUser()
            }
        }

        suspend fun getPublicationById(publicationId: String): PostWithImages? {
            return try {
                firestoreService.getPublicationById(publicationId)
                    .collect { publication ->
                        getAllImagesForPublication.getAllImagesForPublication(
                            currentUserId!!,
                            publicationId,
                        ).collect { images ->
                            if (images.isEmpty()) {
                                Log.e("Storage", "la lista esta vacia ")
                            } else {
                                _images.value = images
                            }
                        }
                        _publication.value = publication
                    }
                _publication.value
            } catch (e: Exception) {
                // Manejar error
                null
            }
        }

        private suspend fun getCurrentUser(): String? {
            return currentUser.getCurrentUser()?.uid
        }
    }

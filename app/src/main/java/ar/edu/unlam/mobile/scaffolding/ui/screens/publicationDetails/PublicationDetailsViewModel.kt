package ar.edu.unlam.mobile.scaffolding.ui.screens.publicationDetails

import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ar.edu.unlam.mobile.scaffolding.domain.models.PostWithImages
import ar.edu.unlam.mobile.scaffolding.domain.services.FirestoreService
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GetAllImagesFromUrl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class
PublicationDetailsViewModel
    @Inject
    constructor(
        private val firestoreService: FirestoreService,
        private val getAllImagesFromUrl: GetAllImagesFromUrl,
    ) : ViewModel() {
        @Suppress("ktlint:standard:backing-property-naming")
        private val _publication = mutableStateOf<PostWithImages?>(null)

        @Suppress("ktlint:standard:backing-property-naming")
        private val _images = mutableStateOf<List<Bitmap>>(emptyList())
        val images: State<List<Bitmap>> = _images

        suspend fun getPublicationById(publicationId: String): PostWithImages? {
            return try {
                firestoreService.getPublicationById(publicationId)
                    .catch {
                        // error
                    }
                    .collect { publication ->
                        getAllImagesFromUrl.getAllImagesFromUrl(publication.images).collect {
                            _images.value = it
                        }
                        _publication.value = publication
                    }
                _publication.value
            } catch (e: Exception) {
                // Manejar error
                null
            }
        }
    }

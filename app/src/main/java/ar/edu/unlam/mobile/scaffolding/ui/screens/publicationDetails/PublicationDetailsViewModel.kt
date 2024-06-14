package ar.edu.unlam.mobile.scaffolding.ui.screens.publicationDetails

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ar.edu.unlam.mobile.scaffolding.domain.models.PostWithImages
import ar.edu.unlam.mobile.scaffolding.domain.services.FirestoreService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class
PublicationDetailsViewModel
    @Inject
    constructor(private val firestoreService: FirestoreService) : ViewModel() {
        @Suppress("ktlint:standard:backing-property-naming")
        private val _publication = mutableStateOf<PostWithImages?>(null)

        suspend fun getPublicationById(publicationId: String): PostWithImages? {
            return try {
                firestoreService.getPublicationById(publicationId)
                    .catch {
                        // error
                    }
                    .collect { publication ->
                        _publication.value = publication
                    }
                _publication.value
            } catch (e: Exception) {
                // Manejar error
                null
            }
        }
    }

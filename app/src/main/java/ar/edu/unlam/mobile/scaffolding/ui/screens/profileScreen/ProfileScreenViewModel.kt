package ar.edu.unlam.mobile.scaffolding.ui.screens.profileScreen

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffolding.domain.models.PostWithImages
import ar.edu.unlam.mobile.scaffolding.domain.models.UserInfoGoogle
import ar.edu.unlam.mobile.scaffolding.domain.services.AuthService
import ar.edu.unlam.mobile.scaffolding.domain.services.FirestoreService
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GetCurrentUser
import ar.edu.unlam.mobile.scaffolding.ui.navigation.NavigationRoutes
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel
    @Inject
    constructor(
        private val getUser: GetCurrentUser,
        private val authService: AuthService,
        private val firestoreService: FirestoreService,
        @ApplicationContext context: Context,
    ) : ViewModel() {
        private val _currentUser = mutableStateOf<UserInfoGoogle?>(null)
        val currentUser: State<UserInfoGoogle?> = _currentUser

        private val _publications = mutableStateOf<List<PostWithImages>>(emptyList())
        val publications: State<List<PostWithImages>> = _publications

        private val _deleteSuccess: MutableStateFlow<Boolean> = MutableStateFlow(false)
        val deleteSuccess: StateFlow<Boolean> = _deleteSuccess

        private val signInClient = Identity.getSignInClient(context)

        init {
            viewModelScope.launch {
                _currentUser.value = getCurrentUser()
                _currentUser.value?.userId?.let { userId ->
                    fetchPublications(userId)
                }
            }
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

        fun fetchPublications(userId: String) {
            viewModelScope.launch {
                try {
                    firestoreService
                        .getPublicationsByUserId(userId)
                        .collect { posts ->
                            _publications.value = posts
                        }
                } catch (e: Exception) {
                    Log.e("ProfileScreenViewModel", "Error fetching publications", e)
                }
            }
        }

        fun deletePublication(
            publicationId: String,
            userId: String,
        ) {
            viewModelScope.launch {
                firestoreService
                    .deletePublicationForUser(userId, publicationId)
                    .catch { e ->
                        _deleteSuccess.value = false
                        e.printStackTrace()
                    }.collect { success ->
                        _deleteSuccess.value = success
                        fetchPublications(userId)
                        Log.d("ProfileScreenViewModel", "Publication deleted: $success")
                    }
            }
        }

        fun deletePublicationInAllPublications(publicationId: String) {
            viewModelScope.launch {
                firestoreService
                    .deletePublicationInAllPublications(publicationId)
                    .catch { e ->
                        // Manejar errores si es necesario
                        e.printStackTrace()
                    }.collect { success ->
                        _deleteSuccess.value = success
                        Log.d("ProfileScreenViewModel", "Publication deleted in all publications: $success")
                    }
            }
        }

        fun signOut() {
            viewModelScope.launch {
                authService.signOut() // cierra sesión en Firebase
                signInClient.signOut() // cierra sesión en Google
            }
        }

        fun navigateToEditScreen(
            controller: NavHostController,
            publicationId: String,
        ) {
            controller.navigate("${NavigationRoutes.PublicationEditScreen.route}/$publicationId")
        }
    }

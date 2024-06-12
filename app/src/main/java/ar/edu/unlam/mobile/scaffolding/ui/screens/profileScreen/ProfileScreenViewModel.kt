package ar.edu.unlam.mobile.scaffolding.ui.screens.profileScreen

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.models.UserInfoGoogle
import ar.edu.unlam.mobile.scaffolding.domain.services.AuthService
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GetCurrentUser
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel
    @Inject
    constructor(
        private val getUser: GetCurrentUser,
        private val authService: AuthService,
        @ApplicationContext context: Context,
    ) : ViewModel() {
        private val _currentUser = mutableStateOf<UserInfoGoogle?>(null)
        val currentUser: State<UserInfoGoogle?> = _currentUser

        private val signInClient = Identity.getSignInClient(context)

        init {
            viewModelScope.launch {
                _currentUser.value = getCurrentUser()
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

        fun signOut() {
            viewModelScope.launch {
                authService.signOut() // cierra sesión en Firebase
                signInClient.signOut() // cierra sesión en Google
            }
        }
    }

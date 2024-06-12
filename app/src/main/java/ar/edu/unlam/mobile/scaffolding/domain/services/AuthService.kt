package ar.edu.unlam.mobile.scaffolding.domain.services

import ar.edu.unlam.mobile.scaffolding.data.repository.AuthRepositoryInterface
import ar.edu.unlam.mobile.scaffolding.domain.models.AuthRes
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GetCurrentUser
import ar.edu.unlam.mobile.scaffolding.domain.usecases.SignInWithGoogle
import ar.edu.unlam.mobile.scaffolding.domain.usecases.SignOut
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class AuthService
    @Inject
    constructor(
        private val authRepository: AuthRepositoryInterface,
    ) : SignInWithGoogle, SignOut, GetCurrentUser {
        override suspend fun signInWithGoogle(credential: AuthCredential): AuthRes<FirebaseUser> {
            return authRepository.signInWithGoogle(credential)
        }

        override suspend fun signOut() {
            authRepository.signOut()
        }

        override suspend fun getCurrentUser(): FirebaseUser? {
            return authRepository.getCurrentUser()
        }
    }

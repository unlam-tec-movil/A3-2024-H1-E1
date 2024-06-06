package ar.edu.unlam.mobile.scaffolding.data.repository

import ar.edu.unlam.mobile.scaffolding.domain.models.AuthRes
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

interface AuthRepositoryInterface {
    suspend fun signInWithGoogle(credential: AuthCredential): AuthRes<FirebaseUser>

    suspend fun signOut()

    suspend fun getCurrentUser(): FirebaseUser?
}

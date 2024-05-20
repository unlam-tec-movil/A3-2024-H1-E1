package ar.edu.unlam.mobile.scaffolding.data.network

import ar.edu.unlam.mobile.scaffolding.domain.models.AuthRes
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

interface AuthNetworkInterface {
    suspend fun signInWithGoogleCredential(credential: AuthCredential): AuthRes<FirebaseUser>

    suspend fun signOut()

    suspend fun getCurrentUser(): FirebaseUser?
}

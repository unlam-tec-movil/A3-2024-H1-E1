package ar.edu.unlam.mobile.scaffolding.data.network

import ar.edu.unlam.mobile.scaffolding.domain.models.AuthRes
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

interface AuthNetworkInterface {
    suspend fun signInWithGoogleCredential(credential: AuthCredential): AuthRes<FirebaseUser>

    suspend fun signOut()

    suspend fun getCurrentUser(): FirebaseUser?

    suspend fun signInWithEmailAndPassword(
        email: String,
        password: String,
    ): AuthRes<FirebaseUser>

    suspend fun createUserWithEmailAndPassword(
        name: String,
        email: String,
        password: String,
    ): AuthRes<FirebaseUser>
}

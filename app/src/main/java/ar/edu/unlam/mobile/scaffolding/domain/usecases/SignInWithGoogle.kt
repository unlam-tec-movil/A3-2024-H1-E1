package ar.edu.unlam.mobile.scaffolding.domain.usecases

import ar.edu.unlam.mobile.scaffolding.domain.models.AuthRes
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

interface SignInWithGoogle {
    suspend fun signInWithGoogle(credential: AuthCredential): AuthRes<FirebaseUser>
}

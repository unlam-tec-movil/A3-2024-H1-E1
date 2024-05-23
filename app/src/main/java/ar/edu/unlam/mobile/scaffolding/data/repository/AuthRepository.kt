package ar.edu.unlam.mobile.scaffolding.data.repository

import ar.edu.unlam.mobile.scaffolding.data.network.AuthNetworkInterface
import ar.edu.unlam.mobile.scaffolding.domain.models.AuthRes
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class AuthRepository
    @Inject
    constructor(
        private val authNetworkInterface: AuthNetworkInterface,
    ) {
        suspend fun signInWithGoogle(credential: AuthCredential): AuthRes<FirebaseUser> {
            return authNetworkInterface.signInWithGoogleCredential(credential)
        }

        suspend fun signOut() {
            authNetworkInterface.signOut()
        }

        suspend fun getCurrentUser(): FirebaseUser? {
            return authNetworkInterface.getCurrentUser()
        }
    }

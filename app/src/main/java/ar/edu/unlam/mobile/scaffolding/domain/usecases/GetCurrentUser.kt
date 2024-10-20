package ar.edu.unlam.mobile.scaffolding.domain.usecases

import com.google.firebase.auth.FirebaseUser

interface GetCurrentUser {
    suspend fun getCurrentUser(): FirebaseUser?
}

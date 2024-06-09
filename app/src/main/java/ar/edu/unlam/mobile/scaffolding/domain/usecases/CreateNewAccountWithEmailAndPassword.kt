package ar.edu.unlam.mobile.scaffolding.domain.usecases

import ar.edu.unlam.mobile.scaffolding.domain.models.AuthRes
import com.google.firebase.auth.FirebaseUser

interface CreateNewAccountWithEmailAndPassword {
    suspend fun createNewAccountWithEmailAndPassword(
        name: String,
        email: String,
        password: String,
    ): AuthRes<FirebaseUser>
}

package ar.edu.unlam.mobile.scaffolding.data.network

import ar.edu.unlam.mobile.scaffolding.domain.models.AuthRes
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthNetworkImpl
    @Inject
    constructor(
        private val firebaseAuth: FirebaseAuth,
    ) : AuthNetworkInterface {
        // intentamos iniciar sesison con la credencial de google
        override suspend fun signInWithGoogleCredential(credential: AuthCredential): AuthRes<FirebaseUser> {
            return try {
                val firebaseUser = firebaseAuth.signInWithCredential(credential).await()
                firebaseUser.user?.let {
                    AuthRes.Success(it)
                } ?: throw Exception("Sign in with Google failed")
            } catch (e: Exception) {
                AuthRes.Error(e.message ?: "Sign in with Google failed")
            }
        }

        // cerramos sesion
        override suspend fun signOut() {
            firebaseAuth.signOut()
        }

        // obtenemos el usuario
        override suspend fun getCurrentUser(): FirebaseUser? {
            return firebaseAuth.currentUser
        }

        override suspend fun signInWithEmailAndPassword(
            email: String,
            password: String,
        ): AuthRes<FirebaseUser> {
            return try {
                val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                val firebaseUser = result.user
                if (firebaseUser != null) {
                    AuthRes.Success(firebaseUser)
                } else {
                    AuthRes.Error("authentication failed")
                }
            } catch (e: Exception) {
                AuthRes.Error(e.message ?: "Ocurrio un problema al auntenticarse")
            }
        }

        override suspend fun createUserWithEmailAndPassword(
            email: String,
            password: String,
        ): AuthRes<FirebaseUser> {
            return try {
                val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                val firebaseUser = result.user
                if (firebaseUser != null) {
                    AuthRes.Success(firebaseUser)
                } else {
                    AuthRes.Error("User creation failed: No user found")
                }
            } catch (e: Exception) {
                AuthRes.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

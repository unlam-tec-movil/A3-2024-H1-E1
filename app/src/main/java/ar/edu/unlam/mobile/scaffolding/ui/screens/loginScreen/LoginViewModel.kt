package ar.edu.unlam.mobile.scaffolding.ui.screens.loginScreen

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import ar.edu.unlam.mobile.scaffolding.domain.models.AuthRes
import ar.edu.unlam.mobile.scaffolding.domain.usecases.SignInWithGoogle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@Suppress("DEPRECATION")
@HiltViewModel
class LoginViewModel
    @Inject
    constructor(
        private val signInWithGoogle: SignInWithGoogle,
        private val context: Context,
    ) : ViewModel() {
        // funcion para loguearte con tu cuenta de google , esta tarea la hace la api de google

        private val googleSignInClient: GoogleSignInClient by lazy {
            val gso =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken("71136976485-s3dodbdvn5n0c48q2v7mhs9gbupd1j69.apps.googleusercontent.com")
                    .requestEmail()
                    .build()
            GoogleSignIn.getClient(context, gso)
        }

        fun handleSignInResult(task: Task<GoogleSignInAccount>): AuthRes<GoogleSignInAccount>? {
            return try {
                val account = task.getResult(ApiException::class.java)
                AuthRes.Success(account)
            } catch (e: ApiException) {
                AuthRes.Error(e.message ?: "Google sign-in ")
            }
        }

        suspend fun signInWithGoogleCredential(credential: AuthCredential): AuthRes<FirebaseUser> {
            return signInWithGoogle.signInWithGoogle(credential)
        }

        fun signInWithGoogle(googleSignInLauncher: ActivityResultLauncher<Intent>) {
            val signInIntent = googleSignInClient.signInIntent
            googleSignInLauncher.launch(signInIntent)
        }
    }

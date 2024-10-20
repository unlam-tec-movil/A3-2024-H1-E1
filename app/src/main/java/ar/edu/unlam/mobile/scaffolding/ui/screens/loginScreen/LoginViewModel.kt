package ar.edu.unlam.mobile.scaffolding.ui.screens.loginScreen

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.models.AuthRes
import ar.edu.unlam.mobile.scaffolding.domain.usecases.SignInWithEmailAndPassword
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
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface LoginUiState {
    object Succes : LoginUiState

    object Loading : LoginUiState

    object Error : LoginUiState
}

@Suppress("DEPRECATION")
@HiltViewModel
class LoginViewModel
    @Inject
    constructor(
        private val signInWithGoogle: SignInWithGoogle,
        private val signInWithEmailAndPassword: SignInWithEmailAndPassword,
        private val context: Context,
    ) : ViewModel() {
        // funcion para loguearte con tu cuenta de google , esta tarea la hace la api de google

        private val _loginUiState = mutableStateOf<LoginUiState>(LoginUiState.Succes)
        val loginUiState: State<LoginUiState> = _loginUiState

        private val _email = mutableStateOf("")
        val email: State<String> = _email

        private val _password = mutableStateOf("")
        val password: State<String> = _password

        fun setEmail(value: String) {
            _email.value = value
        }

        fun setPassword(value: String) {
            _password.value = value
        }

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

        fun sigInWithEmailAndPassword() {
            _loginUiState.value = LoginUiState.Loading
            viewModelScope.launch {
                try {
                    signInWithEmailAndPassword.signInWithEmailAndPassword(email.value, password.value)
                    _loginUiState.value = LoginUiState.Succes
                } catch (e: Exception) {
                    Log.e("sigIn", "failed sign in with email anda password")
                    _loginUiState.value = LoginUiState.Error
                }
            }
        }

        fun validateEmail(value: String): Boolean {
            val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.com$".toRegex()
            return value.matches(emailRegex)
        }

        fun validatePassword(value: String): Boolean {
            val passwordRegex = "^(?=.*[A-Z])(?=.*[0-9]).{4,}$".toRegex()
            return value.matches(passwordRegex)
        }
    }

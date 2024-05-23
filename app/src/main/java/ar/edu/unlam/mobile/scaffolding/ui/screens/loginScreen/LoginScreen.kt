@file:Suppress("ktlint:standard:no-empty-file", "DEPRECATION")

package ar.edu.unlam.mobile.scaffolding.ui.screens.loginScreen

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.domain.models.AuthRes
import ar.edu.unlam.mobile.scaffolding.ui.navigation.NavigationRoutes
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navHostController: NavHostController) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val googleSignInLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { activityResult ->
            when (
                val account =
                    loginViewModel.handleSignInResult(
                        GoogleSignIn.getSignedInAccountFromIntent(activityResult.data),
                    )
            ) {
                is AuthRes.Error -> {
                    Toast.makeText(context, "Error: ${account.errorMessage}", Toast.LENGTH_SHORT)
                        .show()
                }

                is AuthRes.Succes -> {
                    val credential = GoogleAuthProvider.getCredential(account?.data?.idToken, null)
                    scope.launch {
                        val user = loginViewModel.signInWithGoogleCredential(credential)
                        if (user != null) {
                            Toast.makeText(context, "Bienvenido $user", Toast.LENGTH_SHORT).show()
                            navHostController.navigate(NavigationRoutes.MapScreen.route) {
                                popUpTo(NavigationRoutes.MapScreen.route) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                }
                null -> {
                    Toast.makeText(context, "Error Desconocido", Toast.LENGTH_SHORT).show()
                }
            }
        }
    Column(
        modifier =
            Modifier
                .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(bottom = 30.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            FilledTonalButton(
                onClick = {
                    loginViewModel.signInWithGoogle(googleSignInLauncher = googleSignInLauncher)
                },
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 2.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = "Continuar con Google")
                    Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_google),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier.size(30.dp),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    Surface {
        val controller = rememberNavController()
        LoginScreen(controller)
    }
}

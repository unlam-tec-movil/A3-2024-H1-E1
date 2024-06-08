@file:Suppress("ktlint:standard:no-empty-file", "DEPRECATION")

package ar.edu.unlam.mobile.scaffolding.ui.screens.loginScreen

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.domain.models.AuthRes
import ar.edu.unlam.mobile.scaffolding.ui.components.TextFieldEmail
import ar.edu.unlam.mobile.scaffolding.ui.components.TextFieldPassword
import ar.edu.unlam.mobile.scaffolding.ui.navigation.NavigationRoutes
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
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

                is AuthRes.Success -> {
                    val credential = GoogleAuthProvider.getCredential(account?.data?.idToken, null)
                    scope.launch {
                        val user = loginViewModel.signInWithGoogleCredential(credential)

                        Toast.makeText(context, "Bienvenido $user", Toast.LENGTH_SHORT).show()
                        navHostController.navigate(NavigationRoutes.MapScreen.route) {
                            popUpTo(NavigationRoutes.MapScreen.route) {
                                inclusive = true
                            }
                        }
                    }
                }

                null -> {
                    Toast.makeText(context, "Error Desconocido", Toast.LENGTH_SHORT).show()
                }
            }
        }
    // /aca vamos a implementar la login screen con animacion
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.login_animation))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )
    Column(
        modifier =
            Modifier
                .fillMaxSize(),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(250.dp),
        ) {
            LottieAnimation(
                modifier =
                    Modifier
                        .fillMaxSize(),
                composition = composition,
                progress = progress,
            )
        }

        Column(
            modifier = Modifier.padding(start = 25.dp, top = 15.dp, end = 25.dp, bottom = 5.dp),
        ) {
            Text(
                text = "Inicio de Sesion",
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(bottom = 10.dp),
            )
            TextFieldEmail(
                tittle = "Email",
                label = "abcd@gmail.com",
            ) { email ->
                // /manejamos el resultado del email, lo validamos
            }
            Spacer(modifier = Modifier.padding(15.dp))
            TextFieldPassword(
                tittle = "Password",
                label = "password",
            ) { password ->
                // manejamos el resultado de la contraseña aca
            }
        }

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(start = 25.dp, top = 25.dp, end = 25.dp, bottom = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            IconButton(onClick = {
                // /aca iria el when con el estado del boton
                loginViewModel.signInWithGoogle(googleSignInLauncher = googleSignInLauncher)
                // /accion para acceder con google
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_google),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(100.dp),
                )
            }
        }
        // boton de inicio de sesion
        ExtendedFloatingActionButton(
            onClick = { /*TODO*/ },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(start = 25.dp, top = 35.dp, end = 25.dp, bottom = 5.dp),
        ) {
            Text(text = "iniciar sesion")
        }
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(start = 25.dp, top = 10.dp, end = 25.dp, bottom = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(text = "¿No tienes cuenta? ")
            TextButton(onClick = {
                // nos lleva a la pantalla de crear nuevo Usuario
            }) {
                Text(text = "Registrate")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val navHostController = rememberNavController()
    LoginScreen(navHostController = navHostController)
}

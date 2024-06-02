@file:Suppress("ktlint:standard:no-empty-file", "DEPRECATION")

package ar.edu.unlam.mobile.scaffolding.ui.screens.loginScreen

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.domain.models.AuthRes
import ar.edu.unlam.mobile.scaffolding.ui.navigation.NavigationRoutes
import ar.edu.unlam.mobile.scaffolding.ui.theme.backgroundLogin
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
    var visible by remember {
        mutableStateOf(false)
    }
    val textBienvenida =
        "Estamos encantados de tenerte aquí. Nuestra misión es ayudar a reunir a las " +
            "mascotas perdidas con sus seres queridos de la manera más rápida y " +
            "eficiente posible. Sabemos lo angustiante que puede ser perder a una mascota, y estamos aquí para ofrecerte una plataforma sencilla y eficaz para facilitar su búsqueda."

    LaunchedEffect(Unit) {
        visible = true
    }
    Column(
        modifier =
            Modifier
                .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
                    .background(backgroundLogin),
            contentAlignment = Alignment.TopCenter,
        ) {
            Text(
                text = "Titulo de la App",
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive,
            )
            LottieAnimation(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(top = 5.dp),
                composition = composition,
                progress = progress,
            )
        }
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
            contentAlignment = Alignment.Center,
        ) {
            androidx.compose.animation.AnimatedVisibility(
                visible = visible,
                enter =
                    fadeIn(animationSpec = tween(durationMillis = 2000)) +
                        slideInVertically(
                            initialOffsetY = { -40 },
                            animationSpec = tween(durationMillis = 2000),
                        ),
                exit =
                    fadeOut(animationSpec = tween(durationMillis = 2000)) +
                        slideOutVertically(
                            targetOffsetY = { -40 },
                            animationSpec = tween(durationMillis = 2000),
                        ),
            ) {
                Card(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(230.dp)
                            .padding(5.dp)
                            .border(
                                width = 3.dp,
                                color = backgroundLogin,
                                shape = RoundedCornerShape(5.dp),
                            )
                            .background(MaterialTheme.colorScheme.background),
                ) {
                    Text(
                        text = textBienvenida,
                        modifier = Modifier.padding(5.dp),
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Justify,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }

        Box(
            contentAlignment = Alignment.BottomCenter,
        ) {
            FilledTonalButton(
                onClick = {
                    loginViewModel.signInWithGoogle(googleSignInLauncher = googleSignInLauncher)
                },
                modifier =
                    Modifier
                        .padding(top = 10.dp),
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

@Preview(showBackground = true)
@Composable
fun TextPreview() {
    var visible by remember {
        mutableStateOf(false)
    }
    val textBienvenida =
        "Estamos encantados de tenerte aquí. Nuestra misión es ayudar a reunir a las " +
            "mascotas perdidas con sus seres queridos de la manera más rápida y " +
            "eficiente posible. Sabemos lo angustiante que puede ser perder a una mascota, y estamos aquí para ofrecerte una plataforma sencilla y eficaz para facilitar su búsqueda."

    LaunchedEffect(Unit) {
        visible = true
    }
    Box(
        contentAlignment = Alignment.Center,
    ) {
        AnimatedVisibility(
            visible = visible,
            enter =
                fadeIn(animationSpec = tween(durationMillis = 2000)) +
                    slideInVertically(
                        initialOffsetY = { -40 },
                        animationSpec = tween(durationMillis = 2000),
                    ),
            exit =
                fadeOut(animationSpec = tween(durationMillis = 2000)) +
                    slideOutVertically(
                        targetOffsetY = { -40 },
                        animationSpec = tween(durationMillis = 2000),
                    ),
        ) {
            Card(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(5.dp)
                        .border(
                            width = 3.dp,
                            color = backgroundLogin,
                            shape = RoundedCornerShape(5.dp),
                        ),
            ) {
                Text(
                    text = textBienvenida,
                    modifier = Modifier.padding(5.dp),
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Justify,
                )
            }
        }
    }
}

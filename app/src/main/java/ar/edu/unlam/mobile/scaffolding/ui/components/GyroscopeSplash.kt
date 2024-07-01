package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import ar.edu.unlam.mobile.scaffolding.R
import com.airbnb.lottie.compose.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GyroscopeSplash(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onNeverShowAgain: () -> Unit,
) {
    var isChecked by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    var showText by remember { mutableStateOf(false) }
    var showImage by remember { mutableStateOf(false) }
    var showControls by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(500) // Espera 0.5 segundos
        showText = true
        delay(500) // Espera 0.5 segundos
        showImage = true
        delay(500) // Espera 0.5 segundos
        showControls = true
    }

    Box(modifier = modifier) {
        Popup(
            alignment = Alignment.TopCenter,
            properties = PopupProperties(focusable = true, dismissOnClickOutside = false),
        ) {
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                modifier =
                    Modifier
                        .padding(16.dp)
                        .animateContentSize(
                            animationSpec =
                                tween(
                                    durationMillis = 1000, // Duración de la animación en milisegundos
                                    easing = FastOutSlowInEasing, // Easing para suavizar la animación
                                ),
                        ),
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (showText) {
                        Text(
                            text = "Gire su smartphone hacia la derecha para regresar a la lista de publicaciones",
                            modifier = Modifier.padding(10.dp),
                        )
                    }
                    if (showImage) {
                        // Animación de Lottie
                        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.gyroscope))
                        val progress by animateLottieCompositionAsState(
                            composition,
                            iterations = LottieConstants.IterateForever,
                        )
                        LottieAnimation(
                            composition = composition,
                            progress = progress,
                            modifier = Modifier.height(200.dp).padding(top = 8.dp),
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    if (showControls) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = isChecked,
                                    onCheckedChange = { checked ->
                                        isChecked = checked
                                    },
                                )
                                Text(text = "No volver a mostrar")
                            }
                            TextButton(
                                onClick = {
                                    scope.launch {
                                        if (isChecked) {
                                            onNeverShowAgain()
                                        }
                                        onDismiss()
                                    }
                                },
                                modifier = Modifier.padding(end = 5.dp),
                            ) {
                                Text("Cerrar")
                            }
                        }
                    }
                }
            }
        }
    }
}

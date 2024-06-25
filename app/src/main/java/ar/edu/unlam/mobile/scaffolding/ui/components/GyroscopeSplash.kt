package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import ar.edu.unlam.mobile.scaffolding.R
import com.airbnb.lottie.compose.*
import kotlinx.coroutines.launch

@Composable
fun GyroscopeSplash(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onNeverShowAgain: () -> Unit,
) {
    var isChecked by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Box(modifier = modifier) {
        Popup(
            alignment = Alignment.TopCenter,
            properties = PopupProperties(focusable = true, dismissOnClickOutside = false),
        ) {
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                modifier = Modifier.padding(16.dp),
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Gire su smartphone hacia la derecha para regresar a la lista de publicaciones",
                        modifier = Modifier.padding(10.dp),
                    )
                    // Agregar la animación Lottie aquí
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

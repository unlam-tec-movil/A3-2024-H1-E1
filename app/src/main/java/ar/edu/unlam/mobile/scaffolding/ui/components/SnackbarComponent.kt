package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@Composable
fun SnackbarComponent(
    snackbarHostState: SnackbarHostState,
    message: String,
    actionLabel: String? = null,
    modifier: Modifier = Modifier,
    onActionClick: () -> Unit = {},
) {
    val scope = rememberCoroutineScope()

    // Show the snackbar when the component is recomposed with a non-empty message
    if (message.isNotEmpty()) {
        LaunchedEffect(message) {
            scope.launch {
                val result =
                    snackbarHostState.showSnackbar(
                        message = message,
                        actionLabel = actionLabel,
                    )
                if (result == SnackbarResult.ActionPerformed) {
                    onActionClick()
                }
            }
        }
    }

    // SnackbarHost to host the Snackbar
    SnackbarHost(
        hostState = snackbarHostState,
        modifier = modifier,
    )
}

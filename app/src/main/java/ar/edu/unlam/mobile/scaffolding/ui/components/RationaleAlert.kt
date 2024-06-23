package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun RationaleAlert(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    confirmButtonText: String? = null,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Permisos de Localización") },
        text = {
            Text(
                "Para una mejor experiencia, necesitamos acceso a tu ubicación. Por favor, concede los permisos necesarios.",
            )
        },
        confirmButton = {
            confirmButtonText.let {
                TextButton(onClick = onConfirm) {
                    it?.let { it1 -> Text(it1) }
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Entiendo")
            }
        },
    )
}

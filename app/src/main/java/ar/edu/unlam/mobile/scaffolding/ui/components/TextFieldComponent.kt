package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.onPrimaryDark
import com.example.compose.primaryDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldComponent(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isError: Boolean,
    errorMessage: String,
    onTextChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    modifier: Modifier = Modifier,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            color = onPrimaryDark,
        )
        TextField(
            value = value,
            onValueChange = {
                onTextChange(it)
                onValueChange(it)
            },
            placeholder = { Text(placeholder) },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            isError = isError,
            singleLine = singleLine,
            maxLines = maxLines,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .border(3.dp, primaryDark, RoundedCornerShape(10.dp))
                    .semantics {
                        if (isError) error(errorMessage)
                    },
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.colors(
                // Configura los colores del TextField
                focusedIndicatorColor = Color.Transparent, // Subrayado cuando está enfocado
                unfocusedIndicatorColor = Color.Transparent, // Subrayado cuando no está enfocado
                disabledIndicatorColor = Color.Transparent, // Subrayado cuando está deshabilitado
                errorIndicatorColor = Color.Transparent // Subrayado cuando hay un error
            )
        )
        if (isError) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TextFieldComponentPreview() {
    TextFieldComponent(
        title = "Descripcion",
        value = "",
        onValueChange = {},
        placeholder = "descripcion",
        isError = false,
        errorMessage = "",
        onTextChange = {},
    )
}

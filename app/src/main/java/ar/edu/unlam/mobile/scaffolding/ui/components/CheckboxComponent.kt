package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> CheckboxComponent(
    options: List<T>,
    initialSelectedOption: T?,
    onOptionSelected: (T) -> Unit,
    optionToString: (T) -> String,
    isError: Boolean,
    errorMessage: String,
) {
    var selectedOption by remember { mutableStateOf(initialSelectedOption) }

    Column {
        Text("Selecciona una opción:")
        options.forEach { option ->
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clickable {
                            selectedOption = option
                            onOptionSelected(option)
                        }
                        .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = (selectedOption == option),
                    onClick = {
                        selectedOption = option
                        onOptionSelected(option)
                    },
                )
                Text(
                    text = optionToString(option),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp),
                )
            }
        }

        if (isError) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }

    if (initialSelectedOption != null) {
        val foundOption = options.find { it == initialSelectedOption }
        if (foundOption != null) {
            selectedOption = foundOption
            onOptionSelected(foundOption)
        }
    }
}

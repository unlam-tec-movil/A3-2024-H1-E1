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
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> CheckboxComponent(
    options: List<T>,
    selectedOption: MutableState<T?>,
    optionToString: (T) -> String,
) {
    Column {
        options.forEach { option ->
            Row(
                modifier =
                    Modifier.fillMaxWidth()
                        .height(56.dp)
                        .clickable {
                            selectedOption.value = option
                        }
                        .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = (selectedOption.value == option),
                    onClick = { selectedOption.value = option },
                )
                Text(
                    text = optionToString(option),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp),
                )
            }
        }
    }
}

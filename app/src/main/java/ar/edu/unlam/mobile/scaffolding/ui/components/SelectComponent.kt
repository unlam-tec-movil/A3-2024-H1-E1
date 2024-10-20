package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectComponent(
    title: String = "",
    list: List<String>,
    initialSelectedItem: String = "",
    placeholder: String = "",
    onItemSelected: (String) -> Unit,
    isError: Boolean = false,
    dropdownMenuTag: String? = null,
) {
    var isExpanded by remember { mutableStateOf(false) }

    // Variable para manejar el estado del elemento seleccionado
    val updateSelectedItem by rememberUpdatedState(initialSelectedItem)
    var selectedItem by remember { mutableStateOf(initialSelectedItem) }

    LaunchedEffect(key1 = updateSelectedItem) {
        selectedItem = updateSelectedItem
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = title)
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = it },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .testTag(dropdownMenuTag ?: ""), // Aplicamos el segundo modificador con la etiqueta
        ) {
            TextField(
                value = selectedItem, // Convertimos el elemento seleccionado a String
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                },
                modifier =
                    Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                placeholder = { Text(placeholder) },
            )
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false },
            ) {
                list.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            selectedItem = item
                            isExpanded = false
                            onItemSelected(item)
                        },
                    )
                }
            }
        }
        if (isError) {
            Text(
                text = "Campo requerido",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

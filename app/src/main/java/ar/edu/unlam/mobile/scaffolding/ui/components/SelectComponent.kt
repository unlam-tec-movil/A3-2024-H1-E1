package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.onPrimaryDark
import com.example.compose.primaryDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectComponent(
    title: String = "",
    list: List<String>,
    initialSelectedItem: String = "",
    placeholder: String = "",
    onItemSelected: (String) -> Unit,
    isError: Boolean = false,
) {
    var isExpanded by remember { mutableStateOf(false) }

    // Variable para manejar el estado del elemento seleccionado
    val updateSelectedItem by rememberUpdatedState(initialSelectedItem)
    var selectedItem by remember { mutableStateOf(initialSelectedItem) }

    LaunchedEffect(key1 = updateSelectedItem) {
        selectedItem = updateSelectedItem
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            color = onPrimaryDark,
        )
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = it },
            modifier = Modifier.fillMaxWidth(),
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
                        .fillMaxWidth()
                        .border(3.dp, primaryDark, RoundedCornerShape(10.dp)),
                placeholder = { Text(placeholder) },
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    // Configura los colores del TextField
                    focusedIndicatorColor = Color.Transparent, // Subrayado cuando está enfocado
                    unfocusedIndicatorColor = Color.Transparent, // Subrayado cuando no está enfocado
                    disabledIndicatorColor = Color.Transparent, // Subrayado cuando está deshabilitado
                    errorIndicatorColor = Color.Transparent // Subrayado cuando hay un error
                ),
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

@Preview(showBackground = true)
@Composable
fun SelectComponentPreview() {
    SelectComponent(title = "Especie", list = emptyList(), onItemSelected = {})
}

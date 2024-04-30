
package ar.edu.unlam.mobile.scaffolding.ui.screens

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import java.util.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    options: List<String>,
    onOptionSelected: (String) -> Unit,
    onSearchQueryChanged: (String) -> Unit,
) {   val list = listOf("Perro","Gato", "Pajaro", "Tortuga", "Otros Especies")
    var selectedText by remember {
        mutableStateOf(list[0])
    }
    var isExpanded by remember {
        mutableStateOf(false)
    }
    val buttonModifier = Modifier
        .padding(16.dp)
    var fecha by rememberSaveable { mutableStateOf("") }
    val distanceSliderState = remember { mutableStateOf(0f) }
    val expandedState = remember { mutableStateOf(false) }
    val mCalendar: Calendar = Calendar.getInstance()

    val mDatePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _, year: Int, month: Int, day: Int ->
            fecha = "$day/${month + 1}/$year"
        },
        mCalendar.get(Calendar.YEAR),
        mCalendar.get(Calendar.MONTH),
        mCalendar.get(Calendar.DAY_OF_MONTH)


    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "") }
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { /* Implementar la navegacion a pantalla de Lista */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Aplicar Filtros")
                }
            }
        }
    )
    {}
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(16.dp) // Añadir relleno alrededor del contenido
        ) {
            Text("Distancia: ${distanceSliderState.value} km")
            Spacer(modifier = Modifier.height(16.dp))
            Slider(
                value = distanceSliderState.value,
                onValueChange = { newValue -> distanceSliderState.value = newValue },
                valueRange = 0f..10f,
                steps = 1
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text("Fecha")
            Spacer(modifier = Modifier.padding(6.dp))
          //  Box(modifier = Modifier.fillMaxWidth()) {
           // Row(modifier = Modifier.align(Alignment.Center)) {
                    OutlinedTextField(
                        value = fecha,
                        onValueChange = { fecha = it },
                        readOnly = true,
                        label = { Text(text = "dd/mm/yy") }
                    )
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = null,
                        modifier = Modifier.size(60.dp)
                            .padding(4.dp)
                            .clickable {
                                mDatePickerDialog.show()
                            }
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    Text(text = "Especie")
                    Spacer(modifier = Modifier.padding(10.dp))
                    Column(
                        modifier = Modifier.fillMaxSize()
                            .padding(8.dp)
                    ){
                        ExposedDropdownMenuBox(
                            expanded = isExpanded,
                            onExpandedChange = {isExpanded = !isExpanded}
                        ){
                            TextField(
                                modifer = Modifier.menuAnchor(),
                                value = selectedText,
                                onValueChange = {},
                                readOnly =true,
                                trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)}
                            )

                            ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
                                list.forEachIndexed{index, text ->
                                    DropdownMenuItem(
                                        text = { Text(text = text)},
                                        onClick = {
                                            selectedText = list[index]
                                            isExpanded = false
                                        },
                                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                    )

                                }

                            }

                        }
                        Text(text = "has seleccionado: $selectedText")
                    }

          //  }
        }

    }
}

fun TextField(modifer: Modifier, value: String, onValueChange: () -> Unit, readOnly: Boolean, trailingIcon: @Composable () -> Unit) {

}

@Preview(showBackground = true)
@Composable
fun FilterScreenPreview() {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val options = listOf("Option 1", "Option 2", "Option 3")
    val selectedOption = remember { mutableStateOf(options.firstOrNull()) }

    FilterScreen(
        navController = navController,
        snackbarHostState = snackbarHostState,
        options = options,
        onOptionSelected = { option -> selectedOption.value = option },
        onSearchQueryChanged = { /* Implementa la lógica de búsqueda si es necesario */ }
    )


    
}

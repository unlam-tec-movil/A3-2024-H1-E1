package ar.edu.unlam.mobile.scaffolding.ui.screens.filterScreen

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun <reified T> SelectComponent(
    contactList: List<T>,
    label: String,
    crossinline onItemSelected: (T) -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(contactList.firstOrNull()) }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = it },
    ) {
        TextField(
            value = selectedItem?.toString() ?: "",
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            modifier = Modifier.menuAnchor(),
            label = { Text(text = label) },
        )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
        ) {
            contactList.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item.toString()) },
                    onClick = {
                        selectedItem = item
                        isExpanded = false
                        onItemSelected(item)
                    },
                )
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterScreen(
    modifier: Modifier = Modifier,
    controller: NavHostController,
) {
    val list = listOf("","Vista","Busqueda")
    var selectedText by remember {
        mutableStateOf(list[0])
    }
    val list1 = listOf("","Perro", "Gato", "Pajaro", "Tortuga", "Otros Especies")
    var selectedText1 by remember {
        mutableStateOf(list1[0])
    }
    var isExpanded by remember {
        mutableStateOf(false)
    }
    val buttonModifier =
        Modifier
            .padding(16.dp)
    var fecha by rememberSaveable { mutableStateOf("") }
    val distanceSliderState = remember { mutableStateOf(0f) }
    val expandedState = remember { mutableStateOf(false) }
    val mCalendar: Calendar = Calendar.getInstance()

    val mDatePickerDialog =
        DatePickerDialog(
            LocalContext.current,
            { _, year: Int, month: Int, day: Int ->
                fecha = "$day/${month + 1}/$year"
            },
            mCalendar.get(Calendar.YEAR),
            mCalendar.get(Calendar.MONTH),
            mCalendar.get(Calendar.DAY_OF_MONTH),
        )
    Column(
        modifier =
            Modifier
                .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Distancia: ${distanceSliderState.value} km")
        Spacer(modifier = Modifier.height(20.dp))
        Slider(
            value = distanceSliderState.value,
            onValueChange = { newValue -> distanceSliderState.value = newValue },
            valueRange = 0f..10f,
            steps = 1,
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Text("Fecha")
        Spacer(modifier = Modifier.padding(6.dp))

        Row {
            OutlinedTextField(
                value = fecha,
                onValueChange = { fecha = it },
                readOnly = true,
                label = { Text(text = "dd/mm/yy") },
            )
            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = null,
                modifier =
                Modifier.size(60.dp)
                    .padding(4.dp)
                    .clickable {
                        mDatePickerDialog.show()
                    },
            )
        }
        Spacer(modifier = Modifier.padding(10.dp))
        /*Text(text = "Especie")
        Spacer(modifier = Modifier.padding(10.dp))
*/
        SelectComponent(
            contactList = list1,
            label = "Selecciona la especie del animal",
        ) { selectedItem ->
            selectedText1 = selectedItem.toString()
        }
        Spacer (modifier.padding(10.dp))
     /*   Text(text = "Tipo de publicacion" )
        Spacer (modifier.padding(10.dp))
*/
        SelectComponent(
            contactList = list,
            label = "Selecciona un tipo de publicacion",
        ) { selectedItem ->
            selectedText = selectedItem.toString()
        }

        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            horizontalArrangement = Arrangement.End,
        ) {
            TextButton(
                onClick = { controller.popBackStack() },
            ) {
                Text("Cancelar")
            }
            Button(
                onClick = {
                    controller.popBackStack()
                },
                modifier = Modifier,
            ) {
                Text("Aplicar Filtros")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FilterScreenPreview() {
    val controller = rememberNavController()

    FilterScreen(
        controller = controller,
    )
}

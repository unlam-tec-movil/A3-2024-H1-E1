package ar.edu.unlam.mobile.scaffolding.ui.screens.filterSettings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile.scaffolding.ui.components.DatePickerComponent
import ar.edu.unlam.mobile.scaffolding.ui.components.SelectComponent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FilterSettingsScreen(
    modifier: Modifier = Modifier,
    controller: NavHostController,
    viewModel: FilterSettingsViewModel = hiltViewModel(),
) {
    val list = listOf("Avistamiento", "Busqueda", "Dar en adopcion")
    var selectedText by remember {
        mutableStateOf(list[0])
    }
    val list1 = listOf("Perro", "Gato", "Pajaro", "Tortuga", "Otros Especies")
    var selectedText1 by remember {
        mutableStateOf(list1[0])
    }

    val distanceSliderState = remember { mutableFloatStateOf(0f) }
    var dateLost by remember { mutableStateOf("") }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp),
        ) {
            Text("Distancia: ${distanceSliderState.floatValue} km")
            Slider(
                value = distanceSliderState.floatValue,
                onValueChange = { newValue -> distanceSliderState.floatValue = newValue },
                valueRange = 0f..20f,
                steps = 20,
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Fecha de perdida:")
            DatePickerComponent(
                onDateSelected = { selectedDate ->
                },
            )
        }

        Column(
            modifier =
                Modifier
                    .fillMaxWidth(),
        ) {
            Text("Especie")
            SelectComponent(
                list = list1,
            ) { selectedItem ->
                selectedText1 = selectedItem
            }
        }

        Column(
            modifier =
                Modifier
                    .fillMaxWidth(),
        ) {
            Text("Tipo de publicacion")
            SelectComponent(
                list = list,
            ) { selectedItem ->
                selectedText = selectedItem
            }
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

    FilterSettingsScreen(
        controller = controller,
    )
}

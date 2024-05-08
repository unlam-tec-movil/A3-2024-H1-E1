package ar.edu.unlam.mobile.scaffolding.ui.screens.filterScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile.scaffolding.ui.components.DatePickerComponent
import ar.edu.unlam.mobile.scaffolding.ui.components.SelectComponent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FilterScreen(controller: NavHostController) {
    val list = listOf("", "Avistamiento", "Busqueda", "Dar en adopcion")
    var selectedText by remember {
        mutableStateOf(list[0])
    }
    val list1 = listOf("", "Perro", "Gato", "Pajaro", "Tortuga", "Otros Especies")
    var selectedText1 by remember {
        mutableStateOf(list1[0])
    }

    val distanceSliderState = remember { mutableFloatStateOf(0f) }
    var dateLost by remember { mutableStateOf("") }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text("Distancia: ${distanceSliderState.floatValue} km")
        Spacer(modifier = Modifier.height(5.dp))
        Surface(modifier = Modifier.padding(16.dp)) {
            Slider(
                value = distanceSliderState.floatValue,
                onValueChange = { newValue -> distanceSliderState.floatValue = newValue },
                valueRange = 0f..20f,
                steps = 20,
            )
        }
        Spacer(modifier = Modifier.padding(5.dp))
        Text("Selecciona la fecha")
        Spacer(modifier = Modifier.padding(6.dp))

        Row {
            DatePickerComponent { selectedDate ->
                dateLost = selectedDate
            }
        }
        Spacer(modifier = Modifier.padding(10.dp))
        SelectComponent(
            list = list1,
            label = "Selecciona la especie del animal",
        ) { selectedItem ->
            selectedText1 = selectedItem
        }

        Spacer(modifier = Modifier.padding(10.dp))
        SelectComponent(
            list = list,
            label = "Selecciona un tipo de publicacion",
        ) { selectedItem ->
            selectedText = selectedItem
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

package ar.edu.unlam.mobile.scaffolding.ui.screens.filterSettings

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile.scaffolding.ui.components.DatePickerComponent
import ar.edu.unlam.mobile.scaffolding.ui.components.SelectComponent
import com.example.compose.inverseOnSurfaceLight
import com.example.compose.onPrimaryDark

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

    Box(
        modifier =
            modifier
                .fillMaxSize()
                .background(inverseOnSurfaceLight),
    ) {
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
                    colors =
                        SliderDefaults.colors(
                            thumbColor = onPrimaryDark,
                            activeTrackColor = onPrimaryDark,
                            activeTickColor = onPrimaryDark,
                        ),
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "Fecha de perdida:",
                    fontWeight = FontWeight.SemiBold,
                    color = onPrimaryDark,
                )
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
                SelectComponent(
                    title = "Especie",
                    list = list1,
                    onItemSelected = { selectedItem ->
                        selectedText1 = selectedItem
                    },
                )
            }

            Column(
                modifier =
                    Modifier
                        .fillMaxWidth(),
            ) {
                SelectComponent(
                    title = "Tipo de publicacion",
                    list = list,
                    onItemSelected = { selectedItem ->
                        selectedText = selectedItem
                    },
                )
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
                    Text(
                        text = "Cancelar",
                        color = onPrimaryDark,
                    )
                }
                Button(
                    onClick = {
                        controller.popBackStack()
                    },
                    modifier = Modifier,
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = onPrimaryDark,
                        ),
                        ) {
                    Text("Aplicar Filtros")
                }
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

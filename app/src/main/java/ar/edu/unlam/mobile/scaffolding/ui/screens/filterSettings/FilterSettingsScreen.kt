package ar.edu.unlam.mobile.scaffolding.ui.screens.filterSettings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffolding.ui.components.DatePickerComponent
import ar.edu.unlam.mobile.scaffolding.ui.components.SelectComponent
import ar.edu.unlam.mobile.scaffolding.ui.screens.publicationsList.PublicationsListViewModel
import kotlin.math.roundToInt

@Composable
fun FilterSettingsScreen(
    controller: NavHostController,
    filterSettingsViewModel: FilterSettingsViewModel = hiltViewModel(),
    publicationsListViewModel: PublicationsListViewModel = hiltViewModel(),
) {
    val listaTipos = listOf("Avistamiento", "Búsqueda", "Dar en adopción")
    var tipoSeleccionado by remember { mutableStateOf(listaTipos[0]) }
    val listaEspecies = listOf("Perro", "Gato", "Pájaro", "Tortuga", "Otras Especies")
    var especieSeleccionada by remember { mutableStateOf(listaEspecies[0]) }
    val distanciaSliderState = remember { mutableFloatStateOf(0f) }
    var fechaPerdida by remember { mutableStateOf("") }

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
            Text("Distancia: ${distanciaSliderState.value.roundToInt()} km")
            Slider(
                value = distanciaSliderState.value,
                onValueChange = { nuevoValor -> distanciaSliderState.value = nuevoValor },
                valueRange = 0f..20f,
                steps = 20,
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Fecha de pérdida:")
            DatePickerComponent(onDateSelected = { fechaSeleccionada ->
                fechaPerdida = fechaSeleccionada
            })
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            SelectComponent(
                title = "Especie",
                list = listaEspecies,
                onItemSelected = { itemSeleccionado ->
                    especieSeleccionada = itemSeleccionado
                },
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            SelectComponent(
                title = "Tipo de publicación",
                list = listaTipos,
                onItemSelected = { itemSeleccionado ->
                    tipoSeleccionado = itemSeleccionado
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
            TextButton(onClick = { controller.popBackStack() }) {
                Text("Cancelar")
            }
            Button(
                onClick = {
                    filterSettingsViewModel.selectedSpecies = especieSeleccionada
                    filterSettingsViewModel.selectedPublicationType = tipoSeleccionado
                    filterSettingsViewModel.selectedDistance = distanciaSliderState.value
                    filterSettingsViewModel.selectedDateLost = fechaPerdida

                    val filters = filterSettingsViewModel.getFilters()
                    publicationsListViewModel.applyFilters(filters)

                    controller.navigateUp()
                },
            ) {
                Text("Aplicar Filtros")
            }
        }
    }
}

package ar.edu.unlam.mobile.scaffolding.ui.screens.publicationDetails

import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffolding.data.local.DataStoreManager
import ar.edu.unlam.mobile.scaffolding.domain.models.PostWithImages
import ar.edu.unlam.mobile.scaffolding.ui.components.GyroscopeSplash
import ar.edu.unlam.mobile.scaffolding.ui.components.LoadingComponent
import ar.edu.unlam.mobile.scaffolding.ui.components.publicationDetails.PublicationDetails
import ar.edu.unlam.mobile.scaffolding.ui.navigation.NavigationRoutes
import kotlinx.coroutines.launch

private const val GIROSCOPIO_UMBRAL = 1.5f // Define el umbral para el giro hacia atrás

@Composable
fun PublicationDetailsScreen(
    controller: NavHostController,
    viewModel: PublicationDetailsViewModel = hiltViewModel(),
    publicationId: String,
) {
    val uiState: PublicationUiState by viewModel.uiState.collectAsState()
    var selectedPublication by remember { mutableStateOf<PostWithImages?>(null) }
    val context = LocalContext.current
    val dataStoreManager = remember { DataStoreManager }
    dataStoreManager.initialize(context)

    val scope = rememberCoroutineScope()

    var showTooltip by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(Unit) {
        dataStoreManager
            .readFromDataStore(
                DataStoreManager.Keys.SHOW_TOOLTIP,
                true,
            ).collect { value ->
                showTooltip = value
            }
    }

    LaunchedEffect(publicationId) {
        selectedPublication = viewModel.getPublicationById(publicationId)
    }

    when (val publicationState = uiState.publicationState) {
        PublicationState.Loading -> {
            LoadingComponent()
        }

        PublicationState.Error -> {
            Toast.makeText(context, "Error al buscar la informacion", Toast.LENGTH_SHORT).show()
        }

        PublicationState.Success -> {
            Crossfade(targetState = selectedPublication, label = "") { publication ->
                publication?.let {
                    PublicationDetails(
                        it,
                        viewModel.images.value,
                        onBackClick = { controller.navigateUp() },
                    )
                }
            }
            if (showTooltip) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    GyroscopeSplash(
                        onDismiss = {
                            showTooltip = false
                        },
                        onNeverShowAgain = {
                            scope.launch {
                                dataStoreManager.writeToDataStore(
                                    DataStoreManager.Keys.SHOW_TOOLTIP,
                                    false,
                                )
                            }
                        },
                        modifier = Modifier.padding(16.dp),
                    )
                }
            }
            // Escuche los cambios del sensor de giroscopio para navegar hacia atrás
            val rotationY by viewModel.rotationY.collectAsState(initial = 0f)
            LaunchedEffect(rotationY) {
                if (rotationY > GIROSCOPIO_UMBRAL) {
                    controller.navigate(NavigationRoutes.ListScreen.route)
                }
            }
        }
    }
}

package ar.edu.unlam.mobile.scaffolding.ui.screens.publicationDetails

import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffolding.domain.models.PostWithImages
import ar.edu.unlam.mobile.scaffolding.ui.components.LoadingComponent
import ar.edu.unlam.mobile.scaffolding.ui.components.publicationDetails.PublicationDetails

@Composable
fun PublicationDetailsScreen(
    controller: NavHostController,
    viewModel: PublicationDetailsViewModel = hiltViewModel(),
    publicationId: String,
) {
    val uiState: PublicationUiState by viewModel.uiState.collectAsState()
    var selectedPublication by remember { mutableStateOf<PostWithImages?>(null) }
    val context = LocalContext.current

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
                        onBackClick = { controller.popBackStack() },
                    )
                }
            }
        }
    }
}

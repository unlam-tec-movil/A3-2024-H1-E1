package ar.edu.unlam.mobile.scaffolding.ui.screens.publicationDetails

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffolding.domain.models.PostWithImages
import ar.edu.unlam.mobile.scaffolding.ui.components.PublicationDetails

@Composable
fun PublicationDetailsScreen(
    controller: NavHostController,
    viewModel: PublicationDetailsViewModel = hiltViewModel(),
    publicationId: String,
) {
    var selectedPublication by remember { mutableStateOf<PostWithImages?>(null) }

    LaunchedEffect(publicationId) {
        selectedPublication = viewModel.getPublicationById(publicationId)
    }

    Crossfade(targetState = selectedPublication) { publication ->
        publication?.let {
            PublicationDetails(it, viewModel.images.value, onBackClick = { controller.popBackStack() })
        }
    }
}

package ar.edu.unlam.mobile.scaffolding.ui.screens.publicationDetails

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun PublicationDetailsScreen(
    controller: NavHostController,
    viewModel: PublicationDetailsViewModel = hiltViewModel(),
    publicationId: Long,
) {
}

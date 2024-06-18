package ar.edu.unlam.mobile.scaffolding.ui.screens.publicationsList

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffolding.domain.models.PublicationCellModel
import ar.edu.unlam.mobile.scaffolding.ui.components.LoadingComponent
import ar.edu.unlam.mobile.scaffolding.ui.components.PublicationCell
import ar.edu.unlam.mobile.scaffolding.ui.components.SearchBox
import ar.edu.unlam.mobile.scaffolding.ui.navigation.NavigationRoutes

@Composable
fun PublicationsListScreen(
    modifier: Modifier = Modifier,
    controller: NavHostController,
    viewModel: PublicationsListViewModel = hiltViewModel(),
) {
    val uiState: PublicationsUiState by viewModel.uiState.collectAsState()
    val publications: List<PublicationCellModel> by viewModel.publications.collectAsState()
    Log.d("PUBLICATIONS", publications.toString())
    val context = LocalContext.current
    when (val publicationState = uiState.publicationsState) {
        PublicationsState.Loading -> {
            LoadingComponent()
        }

        PublicationsState.Error -> {
            Toast.makeText(context, "Error al buscar la informacion", Toast.LENGTH_SHORT).show()
        }

        PublicationsState.Success -> {
            Column(
                modifier =
                    Modifier
                        .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SearchBox()
                LazyColumn {
                    items(publications) { publication ->
                        PublicationCell(
                            item = publication,
                            onClick = {
                                Log.d("PUBLICATION", publication.toString())

                                controller.navigate(
                                    NavigationRoutes.PublicationScreen.withPublicationId(publication.id),
                                )
                                Log.d("ROUTE", "DETAILS SCREEN")
                            },
                        )
                    }
                }
            }
        }
    }
}

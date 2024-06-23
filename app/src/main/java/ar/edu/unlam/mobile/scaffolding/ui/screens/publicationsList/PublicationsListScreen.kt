package ar.edu.unlam.mobile.scaffolding.ui.screens.publicationsList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffolding.ui.components.PublicationCell
import ar.edu.unlam.mobile.scaffolding.ui.components.SearchBox
import ar.edu.unlam.mobile.scaffolding.ui.navigation.NavigationRoutes

@Composable
fun PublicationsListScreen(
    modifier: Modifier = Modifier,
    controller: NavHostController,
    viewModel: PublicationsListViewModel = hiltViewModel(),
) {
    var query by remember { mutableStateOf("") }

    LaunchedEffect(key1 = query) {
        viewModel.filterPublicationByTittle(query)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SearchBox(
            listForSearch = viewModel.publicationFilter,
            filterList = { query ->
                viewModel.filterPublicationByTittle(query)
            },
            onSearch = {
                // Aquí puedes realizar una acción específica al hacer la búsqueda
            },
            onLeadingIconClick = {
                controller.navigate(NavigationRoutes.FilterScreen.route)
            },
            onTrailingIconClick = {
                controller.navigate(NavigationRoutes.ProfileScreen.route)
            },
            controller = controller,
        )

        // Muestra la lista filtrada de publicaciones
        LazyColumn {
            items(viewModel.publicationFilter.value) { publication ->
                PublicationCell(
                    item = publication,
                    onClick = {
                        controller.navigate(
                            NavigationRoutes.PublicationScreen.withPublicationId(publication.id),
                        )
                    },
                )
            }
        }
    }
}

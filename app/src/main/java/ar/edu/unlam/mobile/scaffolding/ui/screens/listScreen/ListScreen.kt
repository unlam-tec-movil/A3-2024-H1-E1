package ar.edu.unlam.mobile.scaffolding.ui.screens.listScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffolding.domain.models.PublicationCellModel
import ar.edu.unlam.mobile.scaffolding.ui.components.PublicationCell
import ar.edu.unlam.mobile.scaffolding.ui.components.SearchBox
import ar.edu.unlam.mobile.scaffolding.ui.navigation.NavigationRoutes

@Composable
fun ListScreen(controller: NavHostController) {
    val viewModel: ListViewModel = hiltViewModel()
    val list =
        listOf(
            PublicationCellModel(
                id = "1",
                title = "Title",
                description = "Description",
                distance = "Distance",
                imageResourceId = "",
                publicationType = "PublicationType",
            ),
            PublicationCellModel(
                id = "2",
                title = "Title",
                description = "Description",
                distance = "Distance",
                imageResourceId = "",
                publicationType = "PublicationType",
            ),
            PublicationCellModel(
                id = "3",
                title = "Title",
                description = "Description",
                distance = "Distance",
                imageResourceId = "",
                publicationType = "PublicationType",
            ),
            PublicationCellModel(
                id = "1",
                title = "Title",
                description = "Description",
                distance = "Distance",
                imageResourceId = "",
                publicationType = "PublicationType",
            ),
            PublicationCellModel(
                id = "1",
                title = "Title",
                description = "Description",
                distance = "Distance",
                imageResourceId = "",
                publicationType = "PublicationType",
            ),
            PublicationCellModel(
                id = "1",
                title = "Title",
                description = "Description",
                distance = "Distance",
                imageResourceId = "",
                publicationType = "PublicationType",
            ),
            PublicationCellModel(
                id = "1",
                title = "Title",
                description = "Description",
                distance = "Distance",
                imageResourceId = "",
                publicationType = "PublicationType",
            ),
            PublicationCellModel(
                id = "1",
                title = "Title",
                description = "Description",
                distance = "Distance",
                imageResourceId = "",
                publicationType = "PublicationType",
            ),
            PublicationCellModel(
                id = "1",
                title = "Title",
                description = "Description",
                distance = "Distance",
                imageResourceId = "",
                publicationType = "PublicationType",
            ),
            PublicationCellModel(
                id = "1",
                title = "Title",
                description = "Description",
                distance = "Distance",
                imageResourceId = "",
                publicationType = "PublicationType",
            ),
            PublicationCellModel(
                id = "1",
                title = "Title",
                description = "Description",
                distance = "Distance",
                imageResourceId = "",
                publicationType = "PublicationType",
            ),
            PublicationCellModel(
                id = "1",
                title = "Title",
                description = "Description",
                distance = "Distance",
                imageResourceId = "",
                publicationType = "PublicationType",
            ),
        )

    Column(
        modifier =
            Modifier
                .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SearchBox(onLeadingIconClick = {
            controller.navigate(NavigationRoutes.FilterScreen.route)
        })
        LazyColumn {
            items(list) { item ->
                PublicationCell(item, onClick = {})
            }
        }
    }
}

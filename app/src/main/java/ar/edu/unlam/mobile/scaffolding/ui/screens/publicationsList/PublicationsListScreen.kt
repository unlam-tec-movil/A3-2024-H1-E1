package ar.edu.unlam.mobile.scaffolding.ui.screens.publicationsList

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffolding.domain.models.PublicationCellModel

@Composable
fun PublicationsListScreen(
    modifier: Modifier = Modifier,
    controller: NavHostController,
    viewModel: PublicationsListViewModel = hiltViewModel(),
) {
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
}

package ar.edu.unlam.mobile.scaffolding.ui.screens.listScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.unlam.mobile.scaffolding.domain.models.PublicationCellModel
import ar.edu.unlam.mobile.scaffolding.ui.components.PublicationCell
import ar.edu.unlam.mobile.scaffolding.ui.components.SearchBox

@Composable
fun ListScreen() {
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
        SearchBox()
        LazyColumn {
            items(list) { item ->
                PublicationCell(item, onClick = {})
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewItem() {
    ListScreen()
}

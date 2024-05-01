package ar.edu.unlam.mobile.scaffolding.ui.screens.listScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.ui.components.PostListItem

@Composable
fun ListScreen(list: List<String>) {
    LazyColumn(
        contentPadding = PaddingValues(all = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        items(list) {
            PostListItem()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewItem() {
    val list = listOf("1", "2")
    ListScreen(list)
}




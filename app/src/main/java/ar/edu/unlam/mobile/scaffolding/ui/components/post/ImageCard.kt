@file:Suppress("ktlint:standard:no-empty-file")

package ar.edu.unlam.mobile.scaffolding.ui.components.post

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.ui.theme.Pink80

@Composable
fun <T> ImageCard(
    imageList: List<T>,
    modifier: Modifier,
    onImageSelected: (T) -> Unit,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(Pink80, Color.White, Color.White, Color.White),
    ) {
        Carrousel(
            listOfImage = imageList,
            onSelectedITem = onImageSelected,
        )
    }
}

package ar.edu.unlam.mobile.scaffolding.ui.components.post

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import ar.edu.unlam.mobile.scaffolding.domain.models.ImageData

@Composable
fun CarrouselItem(
    item: ImageData,
    pageOffset: Float,
    onItemClick: (ImageData) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier =
            Modifier
                .graphicsLayer {
                    lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f),
                    )
                        .also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }
                    alpha =
                        lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f),
                        )
                }
                .clickable {
                    onItemClick(item)
                },
    ) {
        DisplayImageBitmap(
            data = item.image,
            contentDescription = null,
            modifier =
                Modifier
                    .fillMaxSize(),
        )
    }
}

package ar.edu.unlam.mobile.scaffolding.ui.components.post

import android.graphics.Bitmap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage

@Composable
fun <T> CarrouselItem(
    item: T,
    pageOffset: Float,
    onItemClick: (T) -> Unit,
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
        if (item is Bitmap) {
            DisplayImageBitmap(
                data = item,
                contentDescription = null,
                modifier =
                    Modifier
                        .fillMaxSize(),
            )
        } else {
            AsyncImage(
                model = item,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
        }
    }
}

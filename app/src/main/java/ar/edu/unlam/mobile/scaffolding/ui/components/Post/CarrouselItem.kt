package ar.edu.unlam.mobile.scaffolding.ui.components.Post

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import ar.edu.unlam.mobile.scaffolding.R
import coil.size.Scale

@Composable
fun CarrouselItem(
    item:String?,
    pageOffset: Float,
    onItemClick:(String)->Unit
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        /*animacion de la img , */
        modifier = Modifier
            .graphicsLayer {
                lerp(
                    start = 0.85f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                )
                    .also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }
                alpha = lerp(
                    start = 0.5f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                )
            }
            .clickable {
                item?.let { onItemClick(it) }
            }
            .border(2.dp, Color.LightGray)
    ) {
        CoilImage(
            data = item,
            context = LocalContext.current,
            scale = Scale.FILL,
            crossFade = true,
            contentDescription = null,
            placeHolder = painterResource(id = R.drawable.loading_image),
            error = painterResource(id = R.drawable.images_error),
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

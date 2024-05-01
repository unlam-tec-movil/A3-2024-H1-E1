package ar.edu.unlam.mobile.scaffolding.ui.components.Post

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale

@Composable
fun CoilImage(
    data: String?,
    context: Context,
    scale: Scale = Scale.FILL,
    crossFade:Boolean = false,
    contentDescription:String?,
    placeHolder:Painter? = null,
    error : Painter? = null,
    modifier : Modifier = Modifier
){
    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(data)
            .crossfade(crossFade)
            .scale(scale)
            .build(),
        contentDescription = contentDescription,
        placeholder = placeHolder,
        error = error,
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}
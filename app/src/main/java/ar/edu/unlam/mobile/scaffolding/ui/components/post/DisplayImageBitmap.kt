package ar.edu.unlam.mobile.scaffolding.ui.components.post

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale

@Composable
fun DisplayImageBitmap(
    data: Bitmap,
    contentDescription: String,
    modifier: Modifier = Modifier,
) {
    Image(
        bitmap = data.asImageBitmap(),
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = modifier,
    )
}

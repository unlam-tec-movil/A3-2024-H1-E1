package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.domain.models.SimplifiedPublicationMarker
import ar.edu.unlam.mobile.scaffolding.domain.models.Species
import coil.compose.rememberImagePainter
import com.google.android.gms.maps.model.LatLng

@Composable
fun PublicationDetailsSheet(
    publication: SimplifiedPublicationMarker,
    primaryButtonOnClick: () -> Unit,
) {
    Column(modifier = Modifier.padding(24.dp)) {
        Text(text = "${publication.title}", style = MaterialTheme.typography.headlineMedium)
        Text(
            text = "Tipo de publicacion: ${publication.type + publication.species.getEmoji()}",
            style = MaterialTheme.typography.bodyMedium,
        )
        Row(
            modifier =
                Modifier
                    .height(200.dp)
                    .padding(20.dp),
        ) {
            if (publication.images.isNotEmpty()) {
                val painter = rememberImagePainter(data = publication.images[0])
                Image(
                    painter = painter,
                    contentDescription = "Imagen de la publicación",
                    modifier =
                        Modifier
                            .height(150.dp)
                            .width(150.dp)
                            .clip(RoundedCornerShape(15.dp)),
                    contentScale = ContentScale.Crop,
                )
            }
            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = publication.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Spacer(modifier = Modifier.weight(1f))
            TextButton(onClick = primaryButtonOnClick) {
                Text("Ver más")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPublicationDetailsSheet() {
    // Crea una instancia de SimplifiedPublicationMarker para la vista previa
    val publication =
        SimplifiedPublicationMarker(
            id = "ebc764c8-a433-43ce-8486-50a4f626ca35",
            title = "[Busqueda] Perro Negro Chiquito",
            description = "Estoy en busqueda de mi perro, se llama Juan",
            dateLost = "23/06/2024",
            species = Species.PERRO,
            type = "Busqueda",
            locationCoordinates = LatLng(-34.6549073, -58.5536355),
            images = listOf(""),
        )

    // Llama a tu composable con la publicación de ejemplo
    PublicationDetailsSheet(publication = publication, primaryButtonOnClick = { /* acción del botón */ })
}

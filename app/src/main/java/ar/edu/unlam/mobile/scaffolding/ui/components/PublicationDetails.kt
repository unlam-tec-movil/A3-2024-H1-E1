package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.domain.models.PostWithImages
import ar.edu.unlam.mobile.scaffolding.ui.components.post.Carrousel
import ar.edu.unlam.mobile.scaffolding.ui.utils.capitalizeFirstLetter

@Composable
fun PublicationDetails(
    post: PostWithImages,
    onBackClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(300.dp),
            ) {
               // Carrousel(listOfImage = , paddingValues = 10.dp)
            }

            Text(
                text = post.title,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(vertical = 8.dp),
            )
            HorizontalDivider()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                PublicationDetailItem(label = "Sex", value = post.sex.capitalizeFirstLetter())
                PublicationDetailItem(label = "Age", value = post.age.toString())
            }
            HorizontalDivider()
            PublicationDetailItem(label = "Species", value = post.species.capitalizeFirstLetter())
            HorizontalDivider()
            PublicationDetailItem(label = "Color", value = post.color.capitalizeFirstLetter())
            HorizontalDivider()
            Text(
                text = post.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp),
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ElevatedButton(
                    onClick = {},
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "location",
                        modifier = Modifier.size(18.dp),
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Cómo ir")
                }
                ElevatedButton(
                    onClick = {},
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "share",
                        modifier = Modifier.size(18.dp),
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Compartir")
                }
                ElevatedButton(
                    onClick = {},
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "phone",
                        modifier = Modifier.size(18.dp),
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Contactar")
                }
            }
        }
    }
}

@Composable
fun PublicationDetailItem(
    label: String,
    value: String,
) {
    Row(
        modifier =
            Modifier.padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PublicationDetailsPreview() {
    val today = "12/02/2023"
    val images =
        listOf(
            "https://i.pinimg.com/originals/46/19/87/461987941ab09bdca2ba86c7e34a3ded.jpg",
            "https://i.pinimg.com/originals/46/19/87/461987941ab09bdca2ba86c7e34a3ded.jpg",
        )
    val postPreview =
        PostWithImages(
            "1",
            "search",
            "lost dog",
            "description",
            "$today",
            "dog",
            "female",
            3,
            "black",
            "3",
            1122334455,
            images,
        )
    PublicationDetails(post = postPreview, onBackClick = {})
}

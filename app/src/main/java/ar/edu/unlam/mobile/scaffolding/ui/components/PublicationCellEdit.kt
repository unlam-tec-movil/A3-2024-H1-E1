package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.domain.models.PostWithImages
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.compose.inverseOnSurfaceLight
import com.example.compose.onPrimaryDark
import com.example.compose.scrimLight

@Composable
fun PublicationCellEdit(
    post: PostWithImages,
    onClick: () -> Unit,
    onViewClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Column(
        modifier = Modifier.background(inverseOnSurfaceLight),
        verticalArrangement = Arrangement.Center,
    ) {
        ListItem(
            modifier =
                Modifier
                    .clickable(onClick = onClick)
                    .background(inverseOnSurfaceLight),
            headlineContent = {
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = onPrimaryDark,
                )
            },
            supportingContent = {
                Text(
                    text = post.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            },
            leadingContent = {
                val imagePainter =
                    if (post.images.isNotEmpty()) {
                        rememberAsyncImagePainter(
                            ImageRequest
                                .Builder(LocalContext.current)
                                .data(post.images.first())
                                .apply {
                                    crossfade(true)
                                    transformations(CircleCropTransformation())
                                }.build(),
                        )
                    } else {
                        painterResource(id = R.drawable.default_carrousel_image)
                    }

                Image(
                    painter = imagePainter,
                    contentDescription = null,
                    modifier =
                        Modifier
                            .size(60.dp)
                            .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                )
            },
            trailingContent = {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        IconButton(
                            onClick = onViewClick,
                            colors =
                                IconButtonDefaults.iconButtonColors(
                                    contentColor = scrimLight,
                                ),
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = "View Publication",
                            )
                        }
                        IconButton(
                            onClick = onEditClick,
                            colors =
                                IconButtonDefaults.iconButtonColors(
                                    contentColor = onPrimaryDark,
                                ),
                        ) {
                            Icon(Icons.Filled.Edit, contentDescription = "Edit Publication")
                        }
                        IconButton(
                            onClick = onDeleteClick,
                            colors =
                                IconButtonDefaults.iconButtonColors(
                                    contentColor = MaterialTheme.colorScheme.error,
                                ),
                        ) {
                            Icon(Icons.Filled.Delete, contentDescription = "Delete Publication")
                        }
                    }
                }
            },
            colors =
                ListItemDefaults.colors(
                    containerColor = inverseOnSurfaceLight,
                ),
        )
        HorizontalDivider(color = onPrimaryDark)
    }
}

@Preview(showBackground = true)
@Composable
fun PublicationCellEditPreview() {
    PublicationCellEdit(
        post =
            PostWithImages(
                id = "",
                type = "",
                title = "Michico",
                description = "El perro se perdio mucho tiempo atras",
                dateLost = "",
                sex = "Macho",
                age = 20,
                species = "Perro",
                color = "Black",
                location = "",
                contact = 123456789,
                images =
                    listOf(
                        @Suppress("ktlint:standard:max-line-length")
                        "https://firebasestorage.googleapis.com/v0/b/scaffolding-h1.appspot.com/o/Gc87rhdQDqU5OzoYoYxS8dDFkp33%2F758a46c8-fa08-41e8-b778-a123c7f47742%2Fimage_1719240810169.jpg?alt=media&token=48b49036-62dc-4052-807a-dc659451b131",
                    ),
                locationLat = 0.0,
                locationLng = 0.0,
            ),
        onClick = {},
        onViewClick = {},
        onEditClick = {},
        onDeleteClick = {},
    )
}

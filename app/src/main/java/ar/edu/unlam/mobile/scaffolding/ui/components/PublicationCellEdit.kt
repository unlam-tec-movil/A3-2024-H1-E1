package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.domain.models.PostWithImages

@Composable
fun PublicationCellEdit(
    item: PostWithImages,
    onClick: () -> Unit,
    onViewClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
    ) {
        ListItem(
            modifier =
                Modifier
                    .clickable(onClick = onClick),
            headlineContent = {
                Text(
                    item.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                )
            },
            supportingContent = {
                Text(
                    item.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            },
            leadingContent = {
                Image(
                    painter = painterResource(id = R.drawable.pichi),
                    contentDescription = "",
                    modifier =
                        Modifier
                            .size(50.dp)
                            .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                )
            },
            trailingContent = {
                Row(horizontalArrangement = Arrangement.End) {
                    IconButton(
                        onClick = onViewClick,
                        colors =
                            IconButtonDefaults.iconButtonColors(
                                contentColor = MaterialTheme.colorScheme.onBackground,
                            ),
                    ) {
                        Icon(Icons.Filled.Info, contentDescription = "Ver Publicación")
                    }
                    IconButton(
                        onClick = onEditClick,
                        colors =
                            IconButtonDefaults.iconButtonColors(
                                contentColor = MaterialTheme.colorScheme.primary,
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
            },
        )

        Divider(color = MaterialTheme.colorScheme.outline)
    }
}

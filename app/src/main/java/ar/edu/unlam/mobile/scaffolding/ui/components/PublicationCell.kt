package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
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
import ar.edu.unlam.mobile.scaffolding.domain.models.PublicationCellModel

@Composable
fun PublicationCell(
    item: PublicationCellModel,
    onClick: () -> Unit,
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
                Column {
                    Text(
                        item.publicationType,
                        style = MaterialTheme.typography.labelSmall,
                    )
                    Text(
                        item.distance,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                    )
                }
            },
        )
        HorizontalDivider()
    }
}

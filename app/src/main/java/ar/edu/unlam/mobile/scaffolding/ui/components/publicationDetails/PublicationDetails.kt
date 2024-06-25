package ar.edu.unlam.mobile.scaffolding.ui.components.publicationDetails

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.Toast
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile.scaffolding.domain.models.PostWithImages
import ar.edu.unlam.mobile.scaffolding.ui.components.post.Carrousel
import ar.edu.unlam.mobile.scaffolding.ui.utils.capitalizeFirstLetter
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PublicationDetails(
    post: PostWithImages,
    images: List<Bitmap>,
    onBackClick: () -> Unit,
) {
    val context = LocalContext.current
    var showDialogContact by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 5.dp)
                    .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = "",
                    )
                },
                modifier = Modifier.align(Alignment.End),
                actions = {
                    IconButton(
                        onClick = onBackClick,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "back",
                            modifier = Modifier.size(32.dp),
                        )
                    }
                },
            )
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(350.dp),
            ) {
                Carrousel(listOfImage = images, paddingValues = 10.dp)
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
                    onClick = {
                        val location =
                            Uri.parse("geo:0,0?q=${Uri.encode(post.location)}")
                        val mapIntent = Intent(Intent.ACTION_VIEW, location)

                        try {
                            context.startActivity(mapIntent)
                        } catch (e: ActivityNotFoundException) {
                            Toast
                                .makeText(
                                    context,
                                    "Error al abrir el mapa",
                                    Toast.LENGTH_SHORT,
                                ).show()
                        }
                    },
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "location",
                        modifier = Modifier.size(18.dp),
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Cómo ir",
                        fontSize = 16.sp,
                    )
                }

                ElevatedButton(
                    onClick = { showDialogContact = true },
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "phone",
                        modifier = Modifier.size(18.dp),
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Contactar", fontSize = 16.sp)
                    Log.d("CONTACTAR", "$showDialogContact")
                }
                if (showDialogContact) {
                    ShowContact(
                        phoneNumber = post.contact,
                        onDismiss = {},
                    )
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

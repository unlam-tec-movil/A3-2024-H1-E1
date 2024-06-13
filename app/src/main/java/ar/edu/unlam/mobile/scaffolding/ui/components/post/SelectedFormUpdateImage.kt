package ar.edu.unlam.mobile.scaffolding.ui.components.post

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ar.edu.unlam.mobile.scaffolding.R

@Composable
fun SelectedFormUpdateImage(
    onDissmisButton: () -> Unit,
    onCameraSelected: () -> Unit,
    onGalerrySelected: () -> Unit,
) {
    Dialog(
        onDismissRequest = { onDissmisButton() },
    ) {
        Column(
            modifier =
                Modifier
                    .height(130.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .width(200.dp)
                    .background(
                        MaterialTheme.colorScheme.background,
                    ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Elija una opcion",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            Row(
                modifier = Modifier.padding(15.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier.padding(end = 10.dp)) {
                    IconButton(onClick = {
                        onCameraSelected()
                        onDissmisButton()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_photo_camera_24),
                            contentDescription = null,
                            modifier = Modifier.size(50.dp),
                        )
                    }
                    Text(text = "Camara")
                }
                Column(modifier = Modifier.padding(start = 10.dp)) {
                    IconButton(onClick = {
                        onGalerrySelected()
                        onDissmisButton()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_photo_library_24),
                            contentDescription = null,
                            modifier = Modifier.size(50.dp),
                        )
                    }
                    Text(text = "Galeria")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlerDialogPreview() {
    Surface(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
    ) {
        SelectedFormUpdateImage(onDissmisButton = { /*TODO*/ }, onCameraSelected = { /*TODO*/ }) {
        }
    }
}

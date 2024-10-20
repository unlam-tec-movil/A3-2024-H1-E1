package ar.edu.unlam.mobile.scaffolding.ui.components.post

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ar.edu.unlam.mobile.scaffolding.R

@Composable
fun SettingImage(
    item: Bitmap,
    onDissmissButon: () -> Unit,
    onDeletePhoto: () -> Unit,
) {
    Dialog(
        onDismissRequest = {
            onDissmissButon()
        },
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            colors = CardColors(Color.LightGray, Color.Black, Color.Blue, Color.Blue),
        ) {
            Column {
                IconButton(
                    onClick = { onDissmissButon() },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .size(50.dp)
                            .wrapContentWidth(Alignment.End),
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "cerrar",
                    )
                }
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(350.dp)
                            .clip(shape = RoundedCornerShape(10.dp))
                            .padding(10.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    DisplayImageBitmap(
                        data = item,
                        contentDescription = "1",
                        modifier = Modifier.fillMaxSize(),
                    )
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    ActionButton(
                        text = "Eliminar",
                        icon = painterResource(id = R.drawable.icons_eliminar_img),
                        onClick = {
                            onDeletePhoto()
                            onDissmissButon()
                        },
                    )
                }
            }
        }
    }
}

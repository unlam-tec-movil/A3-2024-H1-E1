package ar.edu.unlam.mobile.scaffolding.ui.components.Post

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ar.edu.unlam.mobile.scaffolding.R
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale

@Composable
fun SettingImage(
    image: String?,
    onDissmissButon:()->Unit,
    onTakePicture:()->Unit,
    onUploadImage:()->Unit,
    onDeleteImage:()->Unit
){
    Dialog(
        onDismissRequest = {
            onDissmissButon()
        }
    ) {

        Card(
            shape = RoundedCornerShape(10.dp),
            colors = CardColors(Color.LightGray, Color.Black, Color.Blue, Color.Blue)
        ) {
            Column(
            ) {
                IconButton(
                    onClick = { onDissmissButon()},
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(50.dp)
                        .wrapContentWidth(Alignment.End)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "cerrar"
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                        .padding(3.dp)
                    ,
                    contentAlignment = Alignment.Center
                ){
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(image)
                            .crossfade(true)
                            .scale(Scale.FIT)
                            .build(),
                        contentDescription = null,
                        placeholder = painterResource(id = R.drawable.loading_image) ,
                        error = painterResource(id = R.drawable.images_error),
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .fillMaxWidth()
                    )
                }
                ///aca voy a poder eliminar o editar una foto
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        ActionButton(text = "Subir", icon = painterResource(id = R.drawable.icon_abrir_galeria), onClick = { onUploadImage() })
                        Spacer(modifier = Modifier.padding(3.dp))
                        ActionButton(text = "Tomar", icon = painterResource(id = R.drawable.icons_tomar_foto), onClick = { onTakePicture() })
                    }
                    Spacer(modifier = Modifier.padding(3.dp))
                    ActionButton(text = "Eliminar", icon = painterResource(id = R.drawable.icons_eliminar_img), onClick = { onDeleteImage() })
                }

            }
        }
    }
}
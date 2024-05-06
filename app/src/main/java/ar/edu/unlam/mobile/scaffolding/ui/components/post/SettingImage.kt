package ar.edu.unlam.mobile.scaffolding.ui.components.post

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ar.edu.unlam.mobile.scaffolding.R
import coil.size.Scale

@Composable
fun SettingImage(
    item: String?,
    onDissmissButon: () -> Unit,
    onUploadPhoto: (() -> Unit)? = null,
    onTakePhoto: (() -> Unit)? = null,
    onDeletePhoto: (() -> Unit)? = null,
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
                    /*en la parte de data va a ir la funcion del viewModel que traiga un elemento de la lista
                    correspondiente a la page,
                     * esto deberia ser un componente aparte ya que lo reutilizo */
                    CoilImage(
                        data = item,
                        context = LocalContext.current,
                        scale = Scale.FILL,
                        crossFade = true,
                        contentDescription = null,
                        placeHolder = painterResource(id = R.drawable.loading_image),
                        error = painterResource(id = R.drawable.images_error),
                        modifier = Modifier.fillMaxSize(),
                    )
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        // en los onclick pasaremos la funcion del viewModel para el que sea correspondiente
                        ActionButton(
                            text = "Subir",
                            icon = painterResource(id = R.drawable.icon_abrir_galeria),
                            onClick = { onUploadPhoto },
                        )
                        Spacer(modifier = Modifier.padding(3.dp))
                        ActionButton(
                            text = "Tomar",
                            icon = painterResource(id = R.drawable.icons_tomar_foto),
                            onClick = { onTakePhoto },
                        )
                    }
                    Spacer(modifier = Modifier.padding(3.dp))
                    ActionButton(
                        text = "Eliminar",
                        icon = painterResource(id = R.drawable.icons_eliminar_img),
                        onClick = { onDeletePhoto },
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DialogImagePreview()  {
    var showDialog by remember {
        mutableStateOf(true)
    }
    Surface(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
    ) {
        SettingImage(
            item = null,
            onDissmissButon = { showDialog = false },
            onDeletePhoto = {},
            onTakePhoto = {},
            onUploadPhoto = {},
        )
    }
}

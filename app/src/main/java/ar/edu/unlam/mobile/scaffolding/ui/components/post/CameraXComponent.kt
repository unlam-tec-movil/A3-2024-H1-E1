package ar.edu.unlam.mobile.scaffolding.ui.components.post

import android.graphics.Bitmap
import androidx.camera.core.CameraSelector
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.ui.components.CameraPreview
import ar.edu.unlam.mobile.scaffolding.ui.components.PhotoBottomSheetContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraXComponent(
    list: State<List<Bitmap>>,
    cameraController: LifecycleCameraController,
    modifier: Modifier,
    onDissmissButton: () -> Unit,
    takePicture: () -> Unit, // /
    ontakePhoto: (Bitmap) -> Unit, // /aca te traen el bitmap
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val context = LocalContext.current

    // /zIndex es para que esta pantalla se superponga a la anterior
    Column(
        modifier = modifier.zIndex(1f),
    ) {
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetContent = {
                PhotoBottomSheetContent(
                    list = list.value,
                    modifier = Modifier.fillMaxWidth(),
                ) { photoSelected ->
                    ontakePhoto(photoSelected)
                    onDissmissButton()
                }
            },
        ) { padding ->
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(padding),
            ) {
                CameraPreview(
                    controller = cameraController,
                    modifier = Modifier.fillMaxSize(),
                )
                IconButton(
                    onClick = { onDissmissButton() },
                    modifier = Modifier.align(Alignment.TopEnd),
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier.size(50.dp),
                        tint = Color.Red,
                    )
                }
                IconButton(
                    onClick = {
                        cameraController.cameraSelector =
                            if (cameraController.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                                CameraSelector.DEFAULT_FRONT_CAMERA
                            } else {
                                CameraSelector.DEFAULT_BACK_CAMERA
                            }
                    },
                    modifier = Modifier.offset(16.dp, 16.dp),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_flip_camera_ios_24),
                        contentDescription = null,
                        modifier = Modifier.size(50.dp),
                    )
                }
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    // icono para abrir la galeria
                    IconButton(onClick = {
                        scope.launch {
                            scaffoldState.bottomSheetState.expand()
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_photo_library_24),
                            contentDescription = null,
                            modifier = Modifier.size(50.dp),
                        )
                    }
                    // icono de tomar una foto
                    IconButton(onClick = {
                        // /esta funcion en el viewModel
                        takePicture()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_photo_camera_24),
                            contentDescription = null,
                            modifier = Modifier.size(50.dp),
                        )
                    }
                }
            }
        }
    }
}

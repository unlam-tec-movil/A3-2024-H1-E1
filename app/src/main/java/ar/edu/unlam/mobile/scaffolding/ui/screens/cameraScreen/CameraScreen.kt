@file:Suppress("ktlint:standard:no-empty-file")

package ar.edu.unlam.mobile.scaffolding.ui.screens.cameraScreen

import androidx.camera.core.CameraSelector
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.ui.components.CameraPreview
import ar.edu.unlam.mobile.scaffolding.ui.components.PhotoBottomSheetContent
import ar.edu.unlam.mobile.scaffolding.ui.navigation.NavigationRoutes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraScreen(navHostController: NavHostController) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val context = LocalContext.current
    val cameraViewModel: CameraViewModel = hiltViewModel()
    val cameraController =
        remember {
            LifecycleCameraController(context).apply {
                setEnabledUseCases(
                    CameraController.IMAGE_CAPTURE,
                )
            }
        }

    val listBitmap by cameraViewModel.listBitmaps.collectAsState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            // pasamos El PhotoBottomSheetContent
            PhotoBottomSheetContent(
                list = listBitmap,
                modifier = Modifier.fillMaxWidth(),
            ) { photoSelected ->
                // funcion para subir la foto seleccionada a firebase
                navHostController.navigate(NavigationRoutes.PublicationEditScreen.route)
            }
        },
    ) { paddingValues ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
        ) {
            // CameraPreview
            CameraPreview(
                controller = cameraController,
                modifier = Modifier.fillMaxSize()
            )

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
                    painter = painterResource(id = R.drawable.icon_cambiar_camara),
                    contentDescription = "saca foto",
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
                IconButton(onClick = {
                    scope.launch {
                        scaffoldState.bottomSheetState.expand()
                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_abrir_galeria),
                        contentDescription = null,
                        modifier = Modifier.size(50.dp),
                        tint = Color.White,
                    )
                }
                // icono para tomar foto
                IconButton(onClick = {
                    cameraViewModel.takePhoto(
                        cameraController,
                        context = context
                    ){ photoTake->
                        cameraViewModel.addPhotoList(photoTake = photoTake)
                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_camara),
                        contentDescription = null,
                        modifier = Modifier.size(50.dp),
                        tint = Color.White,
                    )
                }
            }
        }
    }
}

package ar.edu.unlam.mobile.scaffolding.ui.screens.publicationEdit

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile.scaffolding.domain.models.PetColors
import ar.edu.unlam.mobile.scaffolding.domain.models.Sex
import ar.edu.unlam.mobile.scaffolding.domain.models.SimplifiedPublicationMarker
import ar.edu.unlam.mobile.scaffolding.domain.models.Species
import ar.edu.unlam.mobile.scaffolding.ui.components.CheckboxComponent
import ar.edu.unlam.mobile.scaffolding.ui.components.DatePickerComponent
import ar.edu.unlam.mobile.scaffolding.ui.components.LoadingComponent
import ar.edu.unlam.mobile.scaffolding.ui.components.MapsComponent
import ar.edu.unlam.mobile.scaffolding.ui.components.SelectComponent
import ar.edu.unlam.mobile.scaffolding.ui.components.SnackbarComponent
import ar.edu.unlam.mobile.scaffolding.ui.components.TextFieldComponent
import ar.edu.unlam.mobile.scaffolding.ui.components.post.CameraXComponent
import ar.edu.unlam.mobile.scaffolding.ui.components.post.Carrousel
import ar.edu.unlam.mobile.scaffolding.ui.components.post.SelectedFormUpdateImage
import ar.edu.unlam.mobile.scaffolding.ui.components.post.SettingImage
import ar.edu.unlam.mobile.scaffolding.ui.navigation.NavigationRoutes
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun PublicationEditScreen(
    modifier: Modifier = Modifier,
    controller: NavHostController,
    viewModel: PublicationEditViewModel = hiltViewModel(),
    idPublication: String? = null,
) {
    // /seteamos la variable isEditing
    viewModel.setIsEditing(!idPublication.isNullOrBlank())

    val address by viewModel.address.collectAsState()
    val geocodedLocation by viewModel.geocodedLocation.collectAsState()
    val cameraState = rememberCameraPositionState()
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(geocodedLocation) {
        geocodedLocation?.let {
            cameraState.centerOnLocation(it)
        }
    }

    val scope = rememberCoroutineScope()
    val openCameraX =
        remember {
            mutableStateOf(false)
        }
    var selectedItemForSetting by remember {
        mutableStateOf<Bitmap?>(null)
    }
    var showDialogSelectedUpdateImage by remember {
        mutableStateOf(false)
    }
    var showDialogForSettingImage by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    val context = LocalContext.current
    val cameraXPermission = arrayOf(Manifest.permission.CAMERA)
    val snackbarHostState = remember { SnackbarHostState() }

    val cameraController =
        remember {
            LifecycleCameraController(context).apply {
                setEnabledUseCases(
                    CameraController.IMAGE_CAPTURE,
                )
            }
        }

    val imageBitmapList by viewModel.listImageUser

    val galleryLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri = result.data?.data
                val source =
                    imageUri?.let { img ->
                        ImageDecoder.createSource(context.contentResolver, img)
                    }
                source?.let {
                    val bitmap =
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            ImageDecoder.decodeBitmap(it)
                        } else {
                            null
                        }
                    if (bitmap != null) {
                        if (imageBitmapList.size == 3) {
                            // /solo vamos a dejar que suba 3 imagenes
                            Toast
                                .makeText(
                                    context,
                                    "Solo se permite subir 3 imagenes",
                                    Toast.LENGTH_LONG,
                                ).show()
                        } else {
                            viewModel.addImage(bitmap)
                        }
                    }
                }
            } else {
                Log.d("GoogleFotos", "no image selected")
            }
        }

    LaunchedEffect(viewModel.isEditing) {
        if (viewModel.isEditing.value) {
            viewModel.setPublication(idPublication!!)
        }
    }

    when (viewModel.publicationUiState.value) {
        is PublicationUiState.Loading -> {
            LoadingComponent()
        }

        is PublicationUiState.Success -> {
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Carrousel(
                    listOfImage = imageBitmapList,
                    paddingValues = 10.dp,
                ) { imageSelected ->
                    selectedItemForSetting = imageSelected
                    showDialogForSettingImage = true
                }
                Button(
                    onClick = {
                        showDialogSelectedUpdateImage = true
                    },
                    modifier =
                        Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 5.dp),
                ) {
                    Text(text = "Añadir Foto")
                }

                // RADIO GROUPS
                CheckboxComponent(
                    options = listOf("Busqueda", "Avistamiento", "Dar en adopcion"),
                    initialSelectedOption = viewModel.type.value,
                    onOptionSelected = { selectedOption ->
                        viewModel.setType(selectedOption)
                    },
                    optionToString = { it },
                    isError = viewModel.isErrorType.value,
                    errorMessage = "campo requerido",
                )

                // DATE PICKER COMPONENT
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text("Fecha de perdida:")
                    DatePickerComponent(
                        initialDate = viewModel.dateLost.value,
                        onDateSelected = { selectedDate ->
                            viewModel.setDateLost(selectedDate)
                        },
                    )
                    if (viewModel.isErrorDateLost.value) {
                        Text(
                            text = "Campo requerido",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                }
                // /textField para el titulo
                TextFieldComponent(
                    title = "Titulo",
                    value = viewModel.title.value,
                    onValueChange = { viewModel.setTitle(it) },
                    placeholder = "Ingrese el titulo",
                    isError = viewModel.isErrorTitle.value,
                    errorMessage = "Campo requerido",
                    onTextChange = { viewModel.validateTitle() },
                    singleLine = true,
                )

                TextFieldComponent(
                    title = "Descripcion",
                    value = viewModel.description.value,
                    onValueChange = { viewModel.setDescription(it) },
                    placeholder = "Ingrese la descripcion",
                    isError = viewModel.isErrorDescription.value,
                    errorMessage = "Campo requerido",
                    onTextChange = { viewModel.validateDescription() },
                    maxLines = 4,
                )

                SelectComponent(
                    title = "Especie",
                    list = Species.entries.map { it.name },
                    initialSelectedItem = viewModel.species.value,
                    onItemSelected = { selectedSpecies ->
                        viewModel.setSpecies(selectedSpecies)
                    },
                    isError = viewModel.isErrorSpecies.value,
                )

                SelectComponent(
                    title = "Sexo",
                    Sex.entries.map { it.name },
                    initialSelectedItem = viewModel.sex.value,
                    onItemSelected = { selectedSex ->
                        viewModel.setSex(selectedSex)
                    },
                    isError = viewModel.isErrorSex.value,
                )

                TextFieldComponent(
                    title = "Edad",
                    value = viewModel.age.value,
                    onValueChange = { viewModel.setAge(it) },
                    placeholder = "Ingrese la edad",
                    isError = viewModel.isErrorAge.value,
                    errorMessage = viewModel.messageErrorAge,
                    onTextChange = { viewModel.validateAge() },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    singleLine = true,
                )

                SelectComponent(
                    title = "Color",
                    PetColors.entries.map { it.name },
                    initialSelectedItem = viewModel.color.value,
                    onItemSelected = { selectedColor ->
                        viewModel.setColor(selectedColor)
                    },
                    isError = viewModel.isErrorColor.value,
                )

                TextFieldComponent(
                    title = "Numero de contacto",
                    value = viewModel.contact.value,
                    onValueChange = { viewModel.setContact(it) },
                    placeholder = "Ingrese el numero de contacto",
                    isError = viewModel.isErrorContact.value,
                    errorMessage = viewModel.messageErrorContact,
                    onTextChange = { viewModel.validateContact() },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    singleLine = true,
                )

                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth(),
                ) {
                    TextFieldComponent(
                        title = "Ubicacion",
                        value = address,
                        onValueChange = {
                            viewModel.onAddressChange(it)
                        },
                        placeholder = "Ingrese la ubicacion",
                        isError = viewModel.isErrorLocation.value,
                        errorMessage = "Campo requerido",
                        onTextChange = { viewModel.validateLocation() },
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        keyboardActions =
                            KeyboardActions(onNext = {
                                keyboardController?.hide()
                                scope.launch {
                                    viewModel.geocodeAddress(address)
                                }
                            }),
                        singleLine = true,
                    )

                    MapsComponent(
                        modifier =
                            Modifier
                                .height(250.dp),
                        markers = remember { mutableStateOf<List<SimplifiedPublicationMarker>>(emptyList()) },
                        cameraPositionState = cameraState,
                        isUserLocationEnabled = remember { mutableStateOf(false) },
                        userMarker = geocodedLocation,
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(
                        onClick = {
                            viewModel.resetListOfImages()
                            controller.navigate(NavigationRoutes.MapScreen.route)
                        },
                    ) {
                        Text("Cancelar")
                    }
                    Button(
                        onClick = {
                            // /si nos da true significa que algun campo falta validar , si nos da false significa que todos los campos estan validados correctamente
                            if (!viewModel.validateForm()) {
                                if (viewModel.isEditing.value) {
                                    scope.launch {
                                        viewModel.addEditPublicationToFirestore()
                                        if (viewModel.publicationUiState.value is PublicationUiState.Success) {
                                            controller.navigate(
                                                NavigationRoutes.PublicationScreen.withPublicationId(
                                                    viewModel.id.value,
                                                ),
                                            ) {
                                                if (viewModel.isEditing.value) {
                                                    popUpTo(NavigationRoutes.ProfileScreen.route) {
                                                        inclusive = false
                                                    }
                                                } else {
                                                    popUpTo(NavigationRoutes.MapScreen.route) {
                                                        inclusive = false
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    viewModel.setNewId()
                                    scope.launch {
                                        viewModel.addNewPublication()
                                        if (viewModel.publicationUiState.value is PublicationUiState.Success) {
                                            controller.navigate(
                                                NavigationRoutes.PublicationScreen.withPublicationId(
                                                    viewModel.id.value,
                                                ),
                                            ) {
                                                popUpTo(NavigationRoutes.MapScreen.route) {
                                                    inclusive = false
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                viewModel.setSnackbar(true)
                            }
                        },
                    ) {
                        if (idPublication.isNullOrEmpty()) {
                            Text(text = "Crear Publicacion")
                        } else {
                            Text(text = "Editar Publicacion")
                        }
                    }
                }
            }
        }

        is PublicationUiState.Error -> {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(16.dp),
            ) {
                SnackbarComponent(
                    snackbarHostState = snackbarHostState,
                    message = if (viewModel.snackbar.value) "¡ocurrio un error inesperado!" else "",
                    actionLabel = "Cerrar",
                    modifier = Modifier.align(Alignment.BottomCenter),
                    onActionClick = { viewModel.setSnackbar(false) },
                )
            }
        }
    }
    // /manejamos aca los otros componentes
    if (openCameraX.value) {
        CameraXComponent(
            list = viewModel.listBitmapToCameraX,
            cameraController = cameraController,
            modifier = Modifier.fillMaxSize(),
            onDissmissButton = { openCameraX.value = false },
            takePicture = { viewModel.takePhoto(cameraController, context) },
        ) { photoSelected ->
            viewModel.addImage(photoSelected)
            // /reseteamos las camaras
            viewModel.resetListBitmapToCamareX()
        }
    }
    val requestCameraPermission =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
        ) { isGranted ->
            if (isGranted) {
                openCameraX.value = true
            } else {
                Toast.makeText(context, "Permiso denegado", Toast.LENGTH_SHORT).show()
            }
        }

    val requestGalleryPermission =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
        ) { isGranted ->
            if (isGranted) {
                val galleryIntent =
                    Intent(Intent.ACTION_PICK).apply {
                        setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                    }
                galleryLauncher.launch(galleryIntent)
            } else {
                Toast.makeText(context, "Permiso denegado", Toast.LENGTH_SHORT).show()
            }
        }

    if (showDialogSelectedUpdateImage) {
        SelectedFormUpdateImage(
            onDissmisButton = { showDialogSelectedUpdateImage = false },
            onCameraSelected = {
                if (viewModel.hasRequirePermission(cameraXPermission, context)) {
                    openCameraX.value = true
                } else {
                    requestCameraPermission.launch(Manifest.permission.CAMERA)
                }
            },
            onGalerrySelected = {
                if (viewModel.hasRequirePermission(
                        arrayOf(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            @Suppress("ktlint:standard:max-line-length")
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_MEDIA_IMAGES,
                        ),
                        context,
                    )
                ) {
                    val galleryIntent =
                        Intent(Intent.ACTION_PICK).apply {
                            setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                        }
                    galleryLauncher.launch(galleryIntent)
                } else {
                    requestGalleryPermission.launch(Manifest.permission.READ_MEDIA_IMAGES)
                }
            },
        )
    }
    if (showDialogForSettingImage) {
        SettingImage(
            item = selectedItemForSetting!!,
            onDissmissButon = { showDialogForSettingImage = false },
        ) {
            viewModel.deleteImage(selectedItemForSetting!!)
        }
    }
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
    ) {
        SnackbarComponent(
            snackbarHostState = snackbarHostState,
            message = if (viewModel.snackbar.value) "¡Por favor, complete los campos!" else "",
            actionLabel = "Cerrar",
            modifier = Modifier.align(Alignment.BottomCenter),
            onActionClick = { viewModel.setSnackbar(false) },
        )
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Preview(showBackground = true)
@Composable
fun PublicationEditPreview() {
    val navHostController = rememberNavController()
    PublicationEditScreen(idPublication = null, controller = navHostController)
}

private suspend fun CameraPositionState.centerOnLocation(location: LatLng) =
    animate(
        update =
            CameraUpdateFactory.newLatLngZoom(
                location,
                15f,
            ),
        durationMs = 1500,
    )

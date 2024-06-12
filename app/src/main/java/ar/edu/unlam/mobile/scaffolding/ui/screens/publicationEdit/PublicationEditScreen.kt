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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile.scaffolding.domain.models.PetColors
import ar.edu.unlam.mobile.scaffolding.domain.models.Sex
import ar.edu.unlam.mobile.scaffolding.domain.models.Species
import ar.edu.unlam.mobile.scaffolding.ui.components.CheckboxComponent
import ar.edu.unlam.mobile.scaffolding.ui.components.DatePickerComponent
import ar.edu.unlam.mobile.scaffolding.ui.components.SelectComponent
import ar.edu.unlam.mobile.scaffolding.ui.components.post.CameraXComponent
import ar.edu.unlam.mobile.scaffolding.ui.components.post.Carrousel
import ar.edu.unlam.mobile.scaffolding.ui.components.post.SelectedFormUpdateImage
import ar.edu.unlam.mobile.scaffolding.ui.components.post.SettingImage

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun PublicationEditScreen(
    modifier: Modifier = Modifier,
    controller: NavHostController,
    viewModel: PublicationEditViewModel = hiltViewModel(),
    idPublication: String?,
) {
    val textButton = remember { mutableStateOf("") }

    if (idPublication !== null) {
        // /inicializamos en true la variable is editing
        viewModel.setIsEditing()
        textButton.value = "Editar publicacion"
    } else {
        textButton.value = "Crear publicacion"
    }
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
                            Toast.makeText(context, "Solo se permite subir 3 imagenes", Toast.LENGTH_LONG).show()
                        } else {
                            viewModel.addImage(bitmap)
                        }
                    }
                }
            } else {
                Log.d("GoogleFotos", "no image selected")
            }
        }

    // Resultado de la creacion de la PUBLICACION
    val publicationState by viewModel.publicationState.collectAsState()

    // /porque aca ha dos veces
    LaunchedEffect(idPublication, publicationState) {
        if (idPublication != null) {
            textButton.value = "Editar publicacion"
            viewModel.setPublication(idPublication)
        } else {
            textButton.value = "Crear publicacion"
        }

        publicationState?.let {
            if (it.isSuccess) {
                controller.popBackStack()
            } else if (it.isFailure) {
                // Manejar el error, por ejemplo, mostrar un mensaje de error
                Toast.makeText(context, "Error al crear la publicación", Toast.LENGTH_LONG).show()
            }
        }
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Carrousel(listOfImage = imageBitmapList) { imageSelected ->
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
        Column {
            Text("Selecciona una opción:")
            CheckboxComponent(
                options = listOf("Busqueda", "Avistamiento", "Dar en adopcion"),
                initialSelectedOption = viewModel.type.value,
                onOptionSelected = { selectedOption ->
                    viewModel.setType(selectedOption)
                },
                optionToString = { it },
            )
        }
        // DATE PICKER COMPONENT
        val dateLost by viewModel.dateLost.observeAsState("")
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Fecha de perdida:")
            DatePickerComponent(
                initialDate = dateLost,
                onDateSelected = { selectedDate ->
                    viewModel.setDateLost(selectedDate)
                },
            )
        }

        Column(
            modifier =
                Modifier
                    .fillMaxWidth(),
        ) {
            Text("Titulo de Publicacion")
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.title.value,
                onValueChange = { viewModel.setTitle(it) },
                placeholder = { Text("Ingrese el titulo") },
                singleLine = true,
            )
        }

        Column(
            modifier =
                Modifier
                    .fillMaxWidth(),
        ) {
            Text("Descripcion")
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.description.value,
                onValueChange = { viewModel.setDescription(it) },
                placeholder = { Text("Ingrese la descripcion") },
                maxLines = 4,
            )
        }

        Column(
            modifier =
                Modifier
                    .fillMaxWidth(),
        ) {
            Text("Especie")
            SelectComponent(
                list = Species.values().toList(),
                initialSelectedItem = viewModel.species.value,
            ) { selectedSpecies ->
                viewModel.setSpecies(selectedSpecies.toString())
            }
        }

        Column(
            modifier =
                Modifier
                    .fillMaxWidth(),
        ) {
            Text("Sexo")
            SelectComponent(
                Sex.values().toList(),
                initialSelectedItem = viewModel.sex.value,
            ) { selectedSex ->
                viewModel.setSex(selectedSex.toString())
            }
        }
        Column(
            modifier =
                Modifier
                    .fillMaxWidth(),
        ) {
            Text("Edad")
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.age.value,
                onValueChange = { viewModel.setAge(it) },
                placeholder = { Text("Ingrese la edad") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            )
        }
        Column(
            modifier =
                Modifier
                    .fillMaxWidth(),
        ) {
            Text("Color")
            SelectComponent(
                PetColors.values().toList(),
                initialSelectedItem = viewModel.color.value,
            ) { selectedColor ->
                viewModel.setColor(selectedColor.toString())
            }
        }
        Column(
            modifier =
                Modifier
                    .fillMaxWidth(),
        ) {
            Text("Numero de contacto")
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.contact.value,
                onValueChange = { viewModel.setContact(it) },
                placeholder = { Text("Ingrese el numero de contacto") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            )
        }
        Column(
            modifier =
                Modifier
                    .fillMaxWidth(),
        ) {
            Text("Ubicacion")
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.location.value,
                onValueChange = { viewModel.setLocation(it) },
                placeholder = { Text("Ingrese la ubicacion") },
            )
//            MapsComponent(
//                modifier =
//                    Modifier
//                        .fillMaxWidth()
//                        .height(150.dp)
//                        .clip(RoundedCornerShape(12.dp))
//                        .padding(top = 8.dp),
//            )
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
                    // en caso de cancelar la publicacion entonces reseteamos las imagenes
                    viewModel.resetListOfImages()
                    // en el mismo caso debe ser para los textos
                    controller.popBackStack()
                },
            ) {
                Text("Cancelar")
            }
            Button(
                onClick = {
                    if (viewModel.validateForm()) {
                        if (viewModel.isEditing.value) {
                            viewModel.addEditPublicationToFirestore()
                        } else {
                            viewModel.setNewId()
                            viewModel.addNewPublication()
                        }
                        // /una funcion para que resetee todos los campos de vuelta
                        viewModel.resetListOfImages()
                        viewModel.resetListBitmapToCamareX()
                    } else {
                        // componente o msj para decir que complete los campos
                    }
                    // deberiamos ir a la pantalla de publicationScreen la de ro
                },
                modifier = Modifier,
            ) {
                Text(textButton.value)
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

    if (showDialogSelectedUpdateImage) {
        SelectedFormUpdateImage(
            onDissmisButton = { showDialogSelectedUpdateImage = false },
            onCameraSelected = {
                if (viewModel.hasRequirePermission(cameraXPermission, context)) {
                    openCameraX.value = true
                } else {
                    ActivityCompat.requestPermissions(context as Activity, cameraXPermission, 0)
                }
            },
            onGalerrySelected = {
                val galleryIntent =
                    Intent(Intent.ACTION_PICK).apply {
                        setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                    }
                galleryLauncher.launch(galleryIntent)
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
}

@RequiresApi(Build.VERSION_CODES.P)
@Preview(showBackground = true)
@Composable
fun PublicationEditPreview() {
    val navHostController = rememberNavController()
    PublicationEditScreen(idPublication = null, controller = navHostController)
}

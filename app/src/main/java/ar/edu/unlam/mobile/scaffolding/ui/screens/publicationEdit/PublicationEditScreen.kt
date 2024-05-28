package ar.edu.unlam.mobile.scaffolding.ui.screens.publicationEdit

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffolding.domain.models.ImageData
import ar.edu.unlam.mobile.scaffolding.domain.models.PetColors
import ar.edu.unlam.mobile.scaffolding.domain.models.Sex
import ar.edu.unlam.mobile.scaffolding.domain.models.Species
import ar.edu.unlam.mobile.scaffolding.ui.components.CheckboxComponent
import ar.edu.unlam.mobile.scaffolding.ui.components.DatePickerComponent
import ar.edu.unlam.mobile.scaffolding.ui.components.MapsComponent
import ar.edu.unlam.mobile.scaffolding.ui.components.SelectComponent
import ar.edu.unlam.mobile.scaffolding.ui.components.post.ImageCard
import ar.edu.unlam.mobile.scaffolding.ui.components.post.SelectedFormUpdateImage
import ar.edu.unlam.mobile.scaffolding.ui.components.post.SettingImage
import java.io.File

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun PublicationEditScreen(
    modifier: Modifier = Modifier,
    controller: NavHostController,
    viewModel: PublicationEditViewModel = hiltViewModel(),
) {
    var selectedItemForSetting by remember {
        mutableStateOf<ImageData?>(null)
    }
    var showDialogSelectedUpdateImage by remember {
        mutableStateOf(false)
    }
    var showDialogForSettingImage by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var species by remember { mutableStateOf("") }
    var sex by remember { mutableStateOf("") }
    var dateLost by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    val contactList = listOf("1188223322", "1120332222")
    val scrollState = rememberScrollState()

    val context = LocalContext.current
    val cameraXPermission = arrayOf(Manifest.permission.CAMERA)

    val imageDataList by viewModel.listImageForPublication
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
                        if (imageDataList.size == 3) {
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

    val currentPhotoPath = viewModel.createFile(context)
    val file = File(currentPhotoPath)
    val cameraLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                if (bitmap != null) {
                    if (imageDataList.size == 3)
                        {
                            Toast.makeText(context, "Solo se permite subir 3 imagenes", Toast.LENGTH_LONG).show()
                        } else {
                        viewModel.addImage(bitmap)
                    }
                } else {
                    Log.e("", "failed upload image to list")
                }
            } else {
                Toast.makeText(context, "failed take Photo ${result.resultCode}", Toast.LENGTH_SHORT).show()
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
        ImageCard(
            imageList = imageDataList,
            modifier =
                Modifier
                    .height(350.dp)
                    .width(300.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 5.dp),
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

        if (showDialogSelectedUpdateImage) {
            SelectedFormUpdateImage(
                onDissmisButton = { showDialogSelectedUpdateImage = false },
                onCameraSelected = {
                    if (viewModel.hasRequirePermission(cameraXPermission, context)) {
                        viewModel.captureImage(context, cameraLauncher, file)
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
            // las acciones se hacen cuando este el viewModel
            SettingImage(
                item = selectedItemForSetting!!,
                onDissmissButon = { showDialogForSettingImage = false },
            ) {
                viewModel.deleteImage(selectedItemForSetting!!.image)
            }
        }
        // RADIO GROUPS
        CheckboxComponent(
            options = listOf("Busqueda", "Avistamiento", "Dar en adopcion"),
            selectedOption = remember { mutableStateOf<String?>(null) },
            optionToString = { it },
        )
        // DATE PICKER COMPONENT
        Column(
            modifier =
                Modifier
                    .fillMaxWidth(),
        ) {
            Text("Fecha de perdida:")
            DatePickerComponent { selectedDate ->
                dateLost = selectedDate
            }
        }

        Column(
            modifier =
                Modifier
                    .fillMaxWidth(),
        ) {
            Text("Titulo de Publicacion")
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = title,
                onValueChange = { title = it },
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
                value = description,
                onValueChange = { description = it },
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
            SelectComponent(Species.values().toList()) { selectedSpecies ->
                species = selectedSpecies.toString()
            }
        }

        Column(
            modifier =
                Modifier
                    .fillMaxWidth(),
        ) {
            Text("Sexo")
            SelectComponent(Sex.values().toList()) { selectedSex ->
                sex = selectedSex.toString()
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
                value = age,
                onValueChange = { age = it },
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
            SelectComponent(PetColors.values().toList()) { selectedColor ->
                color = selectedColor.toString()
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
                value = contact,
                onValueChange = { contact = it },
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
                value = location,
                onValueChange = { location = it },
                placeholder = { Text("Ingrese la ubicacion") },
            )
            MapsComponent(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .padding(top = 8.dp),
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
                    controller.popBackStack()
                    // aca llamamos a la funcion createPublication -> donde mandamos la Lista de bitmap
                },
                modifier = Modifier,
            ) {
                Text("Crear Publicacion")
            }
        }
    }
}

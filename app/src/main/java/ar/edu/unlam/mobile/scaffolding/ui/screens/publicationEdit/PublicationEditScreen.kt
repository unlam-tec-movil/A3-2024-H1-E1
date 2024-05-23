package ar.edu.unlam.mobile.scaffolding.ui.screens.publicationEdit

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.ImageDecoder
import android.os.Build
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
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
import androidx.compose.ui.graphics.Color
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
import ar.edu.unlam.mobile.scaffolding.ui.components.MapsComponent
import ar.edu.unlam.mobile.scaffolding.ui.components.SelectComponent
import ar.edu.unlam.mobile.scaffolding.ui.components.post.Carrousel
import ar.edu.unlam.mobile.scaffolding.ui.components.post.SelectedFormUpdateImage
import ar.edu.unlam.mobile.scaffolding.ui.components.post.SettingImage
import ar.edu.unlam.mobile.scaffolding.ui.navigation.NavigationRoutes
import ar.edu.unlam.mobile.scaffolding.ui.theme.Pink80

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun PublicationEditScreen(
    modifier: Modifier = Modifier,
    controller: NavHostController,
    viewModel: PublicationEditViewModel = hiltViewModel(),
) {
    var selectedItemForSetting by remember {
        mutableStateOf("")
    }
    var showDialogSelectedUpadateImage by remember {
        mutableStateOf(false)
    }
    var showDialog by remember { mutableStateOf(false) }
    val openDialog: (String) -> Unit = { selectedItem ->
        // Aquí colocas la lógica para abrir el diálogo
        selectedItemForSetting = selectedItem
        showDialog = true
    }
    val lista: MutableList<String> = mutableListOf()

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
    val permissionRequiere = arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)

    // el gallery launcher
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
                // aca lo convertimos a bitmap y se lo enviamos al vm
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
        Card(
            modifier =
                Modifier
                    .height(350.dp)
                    .width(300.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 5.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardColors(Pink80, Color.White, Color.White, Color.White),
        ) {
            Carrousel(
                listOfImage = lista,
                openDialog,
            )
        }
        Button(
            onClick = {
                if (viewModel.hasRequirePermission(permissionRequiere, context)) {
                    // si nos dieron los permisos vamos a elegir photo o
                    showDialogSelectedUpadateImage = true
                } else {
                    // pedimos permiso
                    ActivityCompat.requestPermissions(
                        context as Activity,
                        permissionRequiere,
                        0,
                    )
                }
            },
            modifier =
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 5.dp),
        ) {
            Text(text = "Añadir Foto")
        }
        if (showDialogSelectedUpadateImage) {
            SelectedFormUpdateImage(
                onDissmisButton = { showDialogSelectedUpadateImage = false },
                onCameraSelected = { controller.navigate(NavigationRoutes.CameraScreen.route) },
            ) {
                // ir a la galeria
                // galeria hacer con un intent
                val pickImageIntent =
                    Intent(Intent.ACTION_PICK).apply {
                        type = "image/*"
                    }
            }
        }
        if (showDialog) {
            // las acciones se hacen cuando este el viewModel
            SettingImage(
                item = selectedItemForSetting,
                onDissmissButon = { showDialog = false },
                onDeletePhoto = null,
            )
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
                onClick = { controller.popBackStack() },
            ) {
                Text("Cancelar")
            }
            Button(
                onClick = {
                    controller.popBackStack()
                },
                modifier = Modifier,
            ) {
                Text("Crear Publicacion")
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Preview(showBackground = true)
@Composable
fun PublicationEditScreenPreview() {
    val controller = rememberNavController()

    PublicationEditScreen(controller = controller)
}

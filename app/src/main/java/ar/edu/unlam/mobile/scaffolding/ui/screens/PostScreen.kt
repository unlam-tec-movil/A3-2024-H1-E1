package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.domain.models.PetColors
import ar.edu.unlam.mobile.scaffolding.domain.models.Sex
import ar.edu.unlam.mobile.scaffolding.domain.models.Species
import ar.edu.unlam.mobile.scaffolding.ui.components.CheckboxComponent
import ar.edu.unlam.mobile.scaffolding.ui.components.DatePickerComponent
import ar.edu.unlam.mobile.scaffolding.ui.components.SelectComponent
import ar.edu.unlam.mobile.scaffolding.ui.components.post.Carrousel
import ar.edu.unlam.mobile.scaffolding.ui.components.post.SettingImage
import ar.edu.unlam.mobile.scaffolding.ui.theme.Pink80

@Composable
fun PostScreen() {
    var selectedItemForSetting by remember {
        mutableStateOf("")
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
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Spacer(modifier = Modifier.padding(5.dp))
        Card(
            modifier =
                Modifier
                    .height(350.dp)
                    .width(300.dp)
                    .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(10.dp),
            colors = CardColors(Pink80, Color.White, Color.White, Color.White),
        ) {
            Carrousel(
                listOfImage = lista,
                openDialog,
            )
        }
        Spacer(modifier = Modifier.padding(5.dp))
        Button(
            onClick = { /*logica para agregar foto*/ },
            modifier = Modifier.align(Alignment.CenterHorizontally),
        ) {
            Text(text = "Añadir Foto")
        }
        if (showDialog) {
            // las acciones se hacen cuando este el viewModel
            SettingImage(
                item = selectedItemForSetting,
                onDissmissButon = { showDialog = false },
                onUploadPhoto = null,
                onTakePhoto = null,
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
        DatePickerComponent { selectedDate ->
            dateLost = selectedDate
        }

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = { title = it },
            label = { Text("Ingrese el titulo") },
            singleLine = true,
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = description,
            onValueChange = { description = it },
            label = { Text("Ingrese la descripcion") },
            maxLines = 4,
        )
        // SELECT COMPONENT
        SelectComponent(Species.values().toList(), "Selecciona una especie") { selectedSpecies ->
            species = selectedSpecies.toString()
        }
        SelectComponent(Sex.values().toList(), "Selecciona el sexo") { selectedSex ->
            sex = selectedSex.toString()
        }
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = age,
            onValueChange = { age = it },
            label = { Text("Ingrese la edad") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        )
        SelectComponent(PetColors.values().toList(), "Selecciona un color") { selectedColor ->
            color = selectedColor.toString()
        }
        SelectComponent(contactList, "Selecciona una forma de contacto") { selectedContact ->
            contact = selectedContact
        }
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = location,
            onValueChange = { location = it },
            label = { Text("Busca localizacion") },
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            // TODO:
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Crear publicacion")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostPreview() {
    PostScreen()
}

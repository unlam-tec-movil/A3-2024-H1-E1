package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.ui.components.Post.Carrousel
import ar.edu.unlam.mobile.scaffolding.ui.components.Post.SettingImage


@Composable
fun PostScreen(
){

    var selectedItemForSetting by remember {
        mutableStateOf("")
    }
    var showDialog by remember { mutableStateOf(false) }
    val openDialog: (String) -> Unit = { selectedItem ->
        // Aquí colocas la lógica para abrir el diálogo
        selectedItemForSetting = selectedItem
        showDialog = true
    }
    var lista:MutableList<String> = mutableListOf(
        "https://depor.com/resizer/crzkitvd2v51Tsyw_Xc1A4enYVs=/1200x900/smart/filters:format(jpeg):quality(75)/cloudfront-us-east-1.images.arcpublishing.com/elcomercio/DAYT2F5NUNB7VPAFKUPHNDXVQA.jpg",
        //"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSdkTKNwX3GMJqhCOCRtwPK2l2Ep9cFMbRoTQ&usqp=CAU"
    )
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        /*Carrousel*/
        Spacer(modifier = Modifier.padding(5.dp))
        if(lista.isNotEmpty()){
            Carrousel(
                listOfImage = lista,
                openDialog
            )
            Spacer(modifier = Modifier.padding(5.dp))
        }
        Button(
            onClick = { /*logica para agregar foto*/ },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Añadir Foto")
        }

        if(showDialog){
            /*las acciones se hacen cuando este el viewModel*/
            SettingImage(
                item = selectedItemForSetting,
                onDissmissButon = {showDialog = false},
                onUploadPhoto = null,
                onTakePhoto = null,
                onDeletePhoto = null
            )
        }


    }
}
@Preview(showBackground = true)
@Composable
fun PostPreview(){
    PostScreen()
}
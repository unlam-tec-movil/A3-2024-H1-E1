package ar.edu.unlam.mobile.scaffolding.ui.screens.mainScreen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile.scaffolding.ui.components.BottomBar
import ar.edu.unlam.mobile.scaffolding.ui.navigation.NavigationComponent

@Composable
fun MainScreen() {
    // Controller es el elemento que nos permite navegar entre pantallas. Tiene las acciones
    // para navegar como navigate y también la información de en dónde se "encuentra" el usuario
    // a través del back stack
    val controller = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(controller = controller) },
        floatingActionButton = {
//            IconButton(onClick = { controller.navigate("home") }) {
//                Icon(Icons.Filled.Add, contentDescription = "add")
//            }
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        },
    ) { paddingValue ->
        NavigationComponent(
            navigationController = controller,
            modifier = Modifier.padding(paddingValue),
        )
    }
}

package ar.edu.unlam.mobile.scaffolding.ui.screens.mainScreen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile.scaffolding.ui.components.BottomBar
import ar.edu.unlam.mobile.scaffolding.ui.navigation.NavigationComponent
import ar.edu.unlam.mobile.scaffolding.ui.navigation.NavigationRoutes

@Composable
fun MainScreen() {
    // Controller es el elemento que nos permite navegar entre pantallas. Tiene las acciones
    // para navegar como navigate y también la información de en dónde se "encuentra" el usuario
    // a través del back stack
    val controller = rememberNavController()
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    print("The currentRoute is: $currentRoute\n")
    Scaffold(
        bottomBar = {
            if (currentRoute == NavigationRoutes.MapScreen.route || currentRoute == NavigationRoutes.Home.route) {
                BottomBar(controller = controller)
            }
        },
        floatingActionButton = {
            if (currentRoute == NavigationRoutes.MapScreen.route || currentRoute == NavigationRoutes.Home.route) {
                FloatingActionButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.Add, "Floating action button.")
                }
            }
        },
    ) { paddingValue ->
        NavigationComponent(
            navigationController = controller,
            modifier = Modifier.padding(paddingValue),
        )
    }
}

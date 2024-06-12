package ar.edu.unlam.mobile.scaffolding.ui.screens.splashScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffolding.ui.navigation.NavigationRoutes

@Composable
fun SplashScreen(navHostController: NavHostController) {
    val splashVm: SplashViewModel = hiltViewModel()

    val isUserAuth = splashVm.isUserAuthenticated.collectAsState()
    LaunchedEffect(isUserAuth.value) {
        splashVm.checkUserAuthenticated()
        when (isUserAuth.value) {
            true -> {
                // si esta logueado nos vamos a la pantalla de mapScreen
                navHostController.navigate(NavigationRoutes.MapScreen.route) {
                    popUpTo(NavigationRoutes.Splash.route) { inclusive = true }
                }
            }

            false -> {
                navHostController.navigate(NavigationRoutes.LoginScreen.route) {
                    popUpTo(NavigationRoutes.Splash.route) { inclusive = true }
                }
            }

            else -> {
                navHostController.navigate(NavigationRoutes.LoginScreen.route) {
                    popUpTo(NavigationRoutes.Splash.route) { inclusive = true }
                }
            }
        }
    }
}

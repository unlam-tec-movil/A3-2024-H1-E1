package ar.edu.unlam.mobile.scaffolding.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ar.edu.unlam.mobile.scaffolding.ui.screens.filterScreen.FilterScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.homeScreen.HomeScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.mapScreen.MapScreen

@Composable
fun NavigationComponent(
    navigationController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navigationController,
        startDestination = NavigationRoutes.MapScreen.route,
        modifier = modifier,
    ) {
        composable(NavigationRoutes.MapScreen.route) {
            MapScreen(controller = navigationController)
        }

        composable(NavigationRoutes.Home.route) {
            HomeScreen()
        }

        composable(NavigationRoutes.FilterScreen.route) {
            FilterScreen(controller = navigationController)
        }
    }
}
/*
For futures implementation you can add here the composable functions for each screen
Here is an example of how to add a new screen

composable(NavigationRoutes.Home.route) { HomeScreen() }
        composable(NavigationRoutes.Profile.route) { ProfileScreen() }
        composable(NavigationRoutes.Login.route) { LoginScreen() }

        //Example of a flow with multiple screens
        navigation(startDestination = OnboardingRoutes.Screen1.route, route = NavigationRoutes.OnboardingFlow.route) {
            composable(OnboardingRoutes.Screen1.route) { OnboardingScreen1() }
            composable(OnboardingRoutes.Screen2.route) { OnboardingScreen2() }
            // Add more onboarding screens as needed
        }
        //Example of a screen with arguments
        composable(Screens.AllCategories.route) { navBackStackEntry ->
            val type = navBackStackEntry.arguments?.getString("type") ?: "Gastos"
            AllCategoriesScreen(controller = navigationController, type = type)
        }
*/

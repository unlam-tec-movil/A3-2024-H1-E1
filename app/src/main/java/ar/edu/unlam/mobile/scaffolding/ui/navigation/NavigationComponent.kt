package ar.edu.unlam.mobile.scaffolding.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ar.edu.unlam.mobile.scaffolding.ui.screens.createAccountScreen.CreateAccountScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.filterSettings.FilterSettingsScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.home.HomeScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.loginScreen.LoginScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.profileScreen.ProfileScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.publicationDetails.PublicationDetailsScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.publicationEdit.PublicationEditScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.publicationsList.PublicationsListScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.publicationsMap.PublicationsMapScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.splashScreen.SplashScreen

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun NavigationComponent(
    navigationController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navigationController,
        startDestination = NavigationRoutes.Splash.route,
        modifier = modifier,
    ) {
        composable(NavigationRoutes.Splash.route) {
            SplashScreen(navHostController = navigationController)
        }
        composable(NavigationRoutes.LoginScreen.route) {
            LoginScreen(navHostController = navigationController)
        }
        composable(NavigationRoutes.CreateAccountScreen.route) {
            CreateAccountScreen(navHostController = navigationController)
        }
        composable(NavigationRoutes.MapScreen.route) {
            PublicationsMapScreen(controller = navigationController)
        }

        composable(NavigationRoutes.ListScreen.route) {
            PublicationsListScreen(controller = navigationController)
        }

        composable(NavigationRoutes.Home.route) {
            HomeScreen()
        }

        composable(NavigationRoutes.PublicationEditScreen.route) {
            PublicationEditScreen(controller = navigationController, idPublication = null)
        }

        composable(
            route = "publicationEditScreen/{idPublication}",
            arguments =
                listOf(
                    navArgument("idPublication") {
                        type = NavType.StringType
                        defaultValue = ""
                        nullable = true
                    },
                ),
        ) { navBackStackEntry ->
            val publicationId = navBackStackEntry.arguments?.getString("idPublication")
            PublicationEditScreen(controller = navigationController, idPublication = publicationId)
        }
        composable(NavigationRoutes.PublicationDetailsScreen.route) { navBackStackEntry ->
            val publicationId = navBackStackEntry.arguments?.getString("publicationId") ?: ""
            PublicationDetailsScreen(
                controller = navigationController,
                publicationId = publicationId,
            )
        }

        composable(NavigationRoutes.FilterScreen.route) {
            FilterSettingsScreen(controller = navigationController)
        }
        composable(NavigationRoutes.ProfileScreen.route) { navBackStackEntry ->
            val publicationId = navBackStackEntry.arguments?.getString("publicationId") ?: ""
            ProfileScreen(
                controller = navigationController,
                publicationId = publicationId,
            )
        }

        composable(NavigationRoutes.PublicationScreen.route) { navBackStackEntry ->
            val publicationId = navBackStackEntry.arguments?.getString("publicationId") ?: ""
            PublicationDetailsScreen(
                controller = navigationController,
                publicationId = publicationId,
            )
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

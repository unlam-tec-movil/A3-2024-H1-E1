package ar.edu.unlam.mobile.scaffolding.ui.navigation

sealed class NavigationRoutes(
    val route: String,
) {
    object Splash : NavigationRoutes("splashScreen")

    object Home : NavigationRoutes("home")

    object MapScreen : NavigationRoutes("mapScreen")

    object ListScreen : NavigationRoutes("listScreen")

    object FilterScreen : NavigationRoutes("filterScreen")

    object PublicationScreen : NavigationRoutes("publicationScreen/{publicationId}") {
        fun withPublicationId(publicationId: String) = "publicationScreen/$publicationId"
    }

    object ProfileScreen : NavigationRoutes("profileScreen")

    object LoginScreen : NavigationRoutes("loginScreen")

    object PublicationDetailsScreen : NavigationRoutes("publicationDetailsScreen/{publicationId}") {
        fun withPublicationId(publicationId: String) = "publicationDetailsScreen/$publicationId"
    }

    object PublicationEditScreen : NavigationRoutes("publicationEditScreen") {
        fun withPublicationId(idPublication: String? = null): String {
            if (idPublication.isNullOrEmpty()) {
                return "publicationEditScreen"
            } else {
                return "publicationEditScreen/$idPublication"
            }
        }
    }

    object CreateAccountScreen : NavigationRoutes("createAccountScreen")
}
/*
For futures screens you can add here the new routes
Here is an example of how to add a new screen with parameters

    object AllCategories : Screens("AllCategories/{type}") {
        fun withType(type: String) = "AllCategories/$type"
    }

 */

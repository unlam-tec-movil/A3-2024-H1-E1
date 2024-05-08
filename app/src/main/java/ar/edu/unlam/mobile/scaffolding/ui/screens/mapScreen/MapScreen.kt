package ar.edu.unlam.mobile.scaffolding.ui.screens.mapScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffolding.ui.components.MapsComponent
import ar.edu.unlam.mobile.scaffolding.ui.components.SearchBox
import ar.edu.unlam.mobile.scaffolding.ui.navigation.NavigationRoutes

@Composable
fun MapScreen(controller: NavHostController) {
    val viewModel: MapScreenViewModel = hiltViewModel()
    Box(
        modifier =
            Modifier
                .fillMaxSize(),
    ) {
        MapsComponent()
        Column(
            modifier =
                Modifier
                    .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SearchBox(onLeadingIconClick = {
                controller.navigate(NavigationRoutes.FilterScreen.route)
            })
            Spacer(
                modifier =
                    Modifier
                        .weight(1f),
            )
        }
    }
}

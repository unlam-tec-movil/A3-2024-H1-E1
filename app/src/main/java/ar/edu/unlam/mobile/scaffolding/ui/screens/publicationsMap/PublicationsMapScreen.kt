package ar.edu.unlam.mobile.scaffolding.ui.screens.publicationsMap

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.ui.components.MapsComponent
import ar.edu.unlam.mobile.scaffolding.ui.components.SearchBox
import ar.edu.unlam.mobile.scaffolding.ui.navigation.NavigationRoutes

@Composable
fun PublicationsMapScreen(
    modifier: Modifier = Modifier,
    controller: NavHostController,
    viewModel: PublicationsMapViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.centerMapOnUserLocation()
        viewModel.getPublicationsMarkers()
    }
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
        FloatingActionButton(
            onClick = { /* Handle click to center map on user location */ },
            modifier =
                Modifier
                    .align(alignment = Alignment.BottomStart)
                    .padding(bottom = 16.dp, start = 16.dp)
                    .size(42.dp),
        ) {
            Icon(painter = painterResource(R.drawable.baseline_my_location_24), contentDescription = "Center map on user location")
        }
    }
}

package ar.edu.unlam.mobile.scaffolding.ui.screens.mapScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ar.edu.unlam.mobile.scaffolding.ui.components.MapsComponent

@Composable
fun MapScreen() {
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
        ) {
            Spacer(
                modifier =
                    Modifier
                        .weight(1f),
            )
        }
    }
}

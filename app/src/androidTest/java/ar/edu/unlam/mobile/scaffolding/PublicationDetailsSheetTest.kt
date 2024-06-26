package ar.edu.unlam.mobile.scaffolding

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import ar.edu.unlam.mobile.scaffolding.domain.models.SimplifiedPublicationMarker
import ar.edu.unlam.mobile.scaffolding.domain.models.Species
import ar.edu.unlam.mobile.scaffolding.ui.components.PublicationDetailsSheet
import com.google.android.gms.maps.model.LatLng
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PublicationDetailsSheetTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun publicationDetailsSheet_DisplayedCorrectly() {
        val publication =
            SimplifiedPublicationMarker(
                id = "ebc764c8-a433-43ce-8486-50a4f626ca35",
                title = "[Busqueda] Perro Negro Chiquito",
                description = "Estoy en busqueda de mi perro, se llama Juan",
                dateLost = "23/06/2024",
                species = Species.PERRO,
                type = "Busqueda",
                locationCoordinates = LatLng(-34.6549073, -58.5536355),
                images = listOf(""),
            )

        composeTestRule.setContent {
            PublicationDetailsSheet(publication = publication, primaryButtonOnClick = { /* acción del botón */ })
        }

        composeTestRule.onNodeWithText("[Busqueda] Perro Negro Chiquito").assertIsDisplayed()
        composeTestRule.onNodeWithText("Tipo de publicacion: Busqueda🐶").assertIsDisplayed()
        composeTestRule.onNodeWithText("Estoy en busqueda de mi perro, se llama Juan").assertIsDisplayed()
        composeTestRule.onNodeWithText("Ver más").assertIsDisplayed()
    }
}

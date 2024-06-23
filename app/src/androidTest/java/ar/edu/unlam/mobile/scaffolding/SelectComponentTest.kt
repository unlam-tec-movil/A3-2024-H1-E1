package ar.edu.unlam.mobile.scaffolding

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import ar.edu.unlam.mobile.scaffolding.ui.components.SelectComponent
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SelectComponentTest {
    @get:Rule
    var composableTestRule = createComposeRule()

    @Test
    fun testSelectComponent() {
        val list = listOf("Perro", "Gato", "Pajaro", "Tortuga", "Otros Especies")
        var selectedItem = ""

        composableTestRule.setContent {
            SelectComponent(
                title = "Especie",
                list = list,
                onItemSelected = { selected ->
                    selectedItem = selected
                },
                placeholder = "Selecione una especie",
                // Asigna la etiqueta al elemento del menú desplegable
                dropdownMenuTag = "menuDesplegable",
            )
        }

        // Haz clic en el menú desplegable para expandirlo
        composableTestRule.onNodeWithTag("menuDesplegable").performClick()
        composableTestRule.waitForIdle()

        // Haz clic en el elemento "Perro"
        composableTestRule.onNodeWithText("Perro").assertExists().performClick()
        assert(selectedItem == "Perro") {
            "Se esperaba que el elemento seleccionado fuera 'Perro', pero fue '$selectedItem'"
        }
    }
}

package ar.edu.unlam.mobile.scaffolding

import androidx.compose.ui.test.junit4.createComposeRule
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
        val list1 = listOf("Perro", "Gato", "Pajaro", "Tortuga", "Otros Especies")
        var selectedItem = ""

        composableTestRule.setContent {
            SelectComponent(
                title = "Especie",
                list = list1,
                onItemSelected = { selected ->
                    selectedItem = selected
                },
            )
        }

        // Busca el nodo de texto que dice "Perro" y realiaza un click
        composableTestRule.onNodeWithText("Perro").performClick()

        // Assert
        assert(selectedItem == "Perro") {
            "Se esperaba que el elemento seleccionado fuera 'Perro', pero fue '$selectedItem'"
        }
    }
}

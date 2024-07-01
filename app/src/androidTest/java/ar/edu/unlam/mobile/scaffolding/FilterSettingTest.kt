package ar.edu.unlam.mobile.scaffolding

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import ar.edu.unlam.mobile.scaffolding.ui.screens.filterSettings.FilterSettingsScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.filterSettings.FilterSettingsViewModel
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FilterSettingsTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testApplyFilters() {
        val navController =
            TestNavHostController(
                ApplicationProvider.getApplicationContext(),
            )

        lateinit var viewModel: FilterSettingsViewModel

        composeTestRule.setContent {
            viewModel = hiltViewModel()
            FilterSettingsScreen(controller = navController, filterSettingsViewModel = viewModel)
        }

        composeTestRule.onNodeWithText("Distancia: 10 km").performClick()
        composeTestRule.onNodeWithText("Aplicar Filtros").performClick()

        val filters = viewModel.getFilters()

        assertEquals("Perro", filters.selectedSpecies)
        assertEquals("Búsqueda", filters.selectedPublicationType)
        assertEquals(10f, filters.selectedDistance)
    }
}

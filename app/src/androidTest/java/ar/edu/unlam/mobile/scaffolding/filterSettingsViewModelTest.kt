package ar.edu.unlam.mobile.scaffolding

import ar.edu.unlam.mobile.scaffolding.domain.models.FilterSettings
import ar.edu.unlam.mobile.scaffolding.ui.screens.filterSettings.FilterSettingsViewModel
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class FilterSettingsViewModelTest {
    private lateinit var viewModel: FilterSettingsViewModel

    @Before
    fun setUp() {
        viewModel = FilterSettingsViewModel()
    }

    @Test
    fun testGetFilters() {
        // Configurar valores de filtro
        viewModel.selectedSpecies = "Perro"
        viewModel.selectedPublicationType = "Búsqueda"
        viewModel.selectedDistance = 10f
        viewModel.selectedDateLost = "2024-06-30"

        // Obtener los filtros aplicados
        val filters: FilterSettings = viewModel.getFilters()

        // Verificar que los filtros se aplicaron correctamente
        assertEquals("Perro", filters.selectedSpecies)
        assertEquals("Búsqueda", filters.selectedPublicationType)
        assertEquals(10f, filters.selectedDistance)
        assertEquals("2024-06-30", filters.selectedDateLost)
    }
}

package ar.edu.unlam.mobile.scaffolding.ui.screens.publicationsList

import ar.edu.unlam.mobile.scaffolding.MainDispatcherRule
import ar.edu.unlam.mobile.scaffolding.domain.models.PostWithImages
import ar.edu.unlam.mobile.scaffolding.domain.services.FirestoreService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals

class PublicationsListViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var firestoreService: FirestoreService

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    private fun mockGetAllFlow(): Flow<List<PostWithImages>> =
        flow {
            emit(emptyList())
        }

    @Test
    fun stateIsSuccessWhenPublicationsAreFetched() =
        runTest {
            // Given
            `when`(firestoreService.getAllPublications()).thenReturn(mockGetAllFlow())
            val expected = PublicationsUiState(PublicationsState.Success)
            val viewModel = PublicationsListViewModel(firestoreService)
            // When
            val actual = viewModel.uiState.value
            // Then
            assertEquals(expected, actual)
        }
}

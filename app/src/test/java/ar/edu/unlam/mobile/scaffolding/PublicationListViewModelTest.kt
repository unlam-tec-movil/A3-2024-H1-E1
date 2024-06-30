package ar.edu.unlam.mobile.scaffolding

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ar.edu.unlam.mobile.scaffolding.domain.models.PostWithImages
import ar.edu.unlam.mobile.scaffolding.domain.services.FirestoreService
import ar.edu.unlam.mobile.scaffolding.ui.screens.publicationsList.PublicationsListViewModel
import ar.edu.unlam.mobile.scaffolding.ui.screens.publicationsList.PublicationsState
import ar.edu.unlam.mobile.scaffolding.ui.screens.publicationsList.PublicationsUiState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class PublicationListViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    private lateinit var firestoreService: FirestoreService
    private lateinit var viewModel: PublicationsListViewModel

    @Before
    fun setUp() {
        firestoreService = mockk()
        viewModel = PublicationsListViewModel(firestoreService)
    }

    @Test
    fun initialStateIsLoading() {
        //given
        val expectedState = PublicationsUiState(PublicationsState.Loading)
        //when
        val state: PublicationsUiState = viewModel.uiState.value
        //then
        assertEquals(expectedState, state)
    }

    @Test
    fun stateIsSuccessWhenPublicationsAreFetched() {
        testScope.runBlockingTest {
            // Given
            val publicationList = listOf(
                PostWithImages(
                    id = "1",
                    type = "adoption",
                    title = "Title",
                    description = "description",
                    dateLost = "2022-01-01",
                    species = "dog",
                    sex = "macho",
                    age = 4,
                    color = "negro",
                    location = "Buenos Aires",
                    contact = 123456789,
                    images = emptyList(),
                    locationLat = 1.0,
                    locationLng = 1.0,
                )
            )
            // simula la respuesta de firebase
            coEvery { firestoreService.getAllPublications() } returns flow { emit(publicationList) }

            viewModel.publications.value
            
            assertEquals(PublicationsState.Loading, viewModel.uiState.value.publicationsState)

            advanceUntilIdle() //avanza el tiempo de la coroutine

            assertEquals(PublicationsState.Success, viewModel.uiState.value.publicationsState)
        }
    }
}

package ar.edu.unlam.mobile.scaffolding.ui.screens.listScreen

import android.media.Image
import androidx.lifecycle.ViewModel
import ar.edu.unlam.mobile.scaffolding.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@Immutable
sealed interface ImageState {
    data class Success(val imageState: Int) : ImageState

    data class Error(val imageState: Image) : ImageState

    data object Loading : ImageState
}

data class ListUIState(val imageState: ImageState)

@HiltViewModel
class ListViewModel
    @Inject
    constructor() : ViewModel() {
        private val imagePet = MutableStateFlow(ImageState.Loading)

        private val _uiState = MutableStateFlow(ListUIState(imagePet.value))

        val uiState = _uiState.asStateFlow()

        init {
            _uiState.value = ListUIState(ImageState.Success(R.drawable.pichi))
        }
    }

package ar.edu.unlam.mobile.scaffolding.ui.screens.splashScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GetCurrentUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel
    @Inject
    constructor(
        private val authUser: GetCurrentUser,
    ) : ViewModel() {
        @Suppress("ktlint:standard:property-naming")
        private val isUserAuthenticate_ = MutableStateFlow<Boolean?>(null)
        val isUserAuthenticated: StateFlow<Boolean?> = isUserAuthenticate_

        // cuando iniciamos la activity tiene que traer el user
        init {
            viewModelScope.launch {
                checkUserAuthenticated()
            }
        }

        suspend fun checkUserAuthenticated() {
            val currentUser = authUser.getCurrentUser()
            isUserAuthenticate_.value = currentUser != null
        }
    }

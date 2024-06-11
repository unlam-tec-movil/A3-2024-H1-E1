package ar.edu.unlam.mobile.scaffolding.ui.screens.createAccountScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.usecases.CreateNewAccountWithEmailAndPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateAccountViewModel
    @Inject
    constructor(
        private val createNewAccountWithEmailAndPassword: CreateNewAccountWithEmailAndPassword,
    ) : ViewModel() {
        private val _email = mutableStateOf("")
        val email: State<String> = _email

        private val _name = mutableStateOf("")
        val name: State<String> = _name

        private val _password = mutableStateOf("")
        val password: State<String> = _password

        fun setEmail(value: String) {
            _email.value = value
        }

        fun setName(value: String) {
            _name.value = value
        }

        fun setPassword(value: String) {
            _password.value = value
        }

        fun createNewAccount() {
            viewModelScope.launch {
                createNewAccountWithEmailAndPassword.createNewAccountWithEmailAndPassword(
                    name.value,
                    email.value,
                    password.value,
                )
            }
        }

        // /me molesta tener estas dos validaciones repetidas
        fun validateEmail(value: String): Boolean {
            val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.com$".toRegex()
            return value.matches(emailRegex)
        }

        fun validatePassword(value: String): Boolean {
            val passwordRegex = "^(?=.*[A-Z])(?=.*[0-9]).{4,}$".toRegex()
            return value.matches(passwordRegex)
        }
    }

package ar.edu.unlam.mobile.scaffolding

import android.content.Context
import ar.edu.unlam.mobile.scaffolding.data.network.AuthNetworkImpl
import ar.edu.unlam.mobile.scaffolding.domain.usecases.SignInWithEmailAndPassword
import ar.edu.unlam.mobile.scaffolding.domain.usecases.SignInWithGoogle
import ar.edu.unlam.mobile.scaffolding.ui.screens.loginScreen.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AuthRepositoryTest {
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var authNetworktImp: AuthNetworkImpl

    @Mock
    private lateinit var signInWithGoogle: SignInWithGoogle

    @Mock
    private lateinit var signInWithEmailAndPassword: SignInWithEmailAndPassword

    @Mock
    private lateinit var context: Context

    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        loginViewModel = LoginViewModel(signInWithGoogle, signInWithEmailAndPassword, context)
        firebaseAuth = Mockito.mock(FirebaseAuth::class.java)
        authNetworktImp = AuthNetworkImpl(firebaseAuth)
    }

    @Test
    fun validatePasswordIsFalse() {
        // given
        val passwordIncorrect = "123456"
        // when
        val response = loginViewModel.validatePassword(passwordIncorrect)
        // then
        assertEquals(false, response)
    }

    @Test
    fun validatePasswordIsTrue() {
        val passwordCorrect = "IsaiasEmir00"

        val response = loginViewModel.validatePassword(passwordCorrect)

        assertEquals(true, response)
    }

    @Test
    fun testGetCurrentUserIsNull() =
        runTest {
            `when`(firebaseAuth.currentUser).thenReturn(null)

            val userActual = authNetworktImp.getCurrentUser()

            assertEquals(userActual, null)
        }

    @Test
    fun testGetCurrentUserIsNotNull() =
        runTest {
            val user: FirebaseUser = Mockito.mock(FirebaseUser::class.java)
            `when`(firebaseAuth.currentUser).thenReturn(user)

            val response = authNetworktImp.getCurrentUser()

            assertEquals(user, response)
        }
}

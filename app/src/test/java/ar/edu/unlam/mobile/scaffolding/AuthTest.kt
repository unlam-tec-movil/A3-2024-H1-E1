package ar.edu.unlam.mobile.scaffolding

import ar.edu.unlam.mobile.scaffolding.data.network.AuthNetworkImpl
import ar.edu.unlam.mobile.scaffolding.domain.models.AuthRes
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import kotlin.test.Test

class AuthTest {
    @Mock
    private lateinit var firebaseAuth: FirebaseAuth

    @Mock
    private lateinit var authResult: AuthResult

    @Mock
    private lateinit var firebaseUser: FirebaseUser

    @InjectMocks
    private lateinit var authNetwork: AuthNetworkImpl

    // /injectamos mockito
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `test signInWithEmailAndPassword success`() =
        runTest {
            val email = "test@example.com"
            val password = "Password12345"

            // Mock the FirebaseAuth behavior
            `when`(firebaseAuth.signInWithEmailAndPassword(email, password)).thenReturn(mockTask(authResult))
            `when`(authResult.user).thenReturn(firebaseUser)

            // Call the function and verify the result
            val result = authNetwork.signInWithEmailAndPassword(email, password)
            assert(result is AuthRes.Success)
            assert((result as AuthRes.Success).data == firebaseUser)
        }

    private suspend fun <T> mockTask(result: T): Task<T> =
        mock(Task::class.java).apply {
            `when`(isSuccessful).thenReturn(true)
            `when`(await()).thenReturn(result)
        } as Task<T>
}

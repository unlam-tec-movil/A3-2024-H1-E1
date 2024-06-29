package ar.edu.unlam.mobile.scaffolding

import ar.edu.unlam.mobile.scaffolding.data.sensors.GyroscopeSensor
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GetGyroscopeDataUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetGyroscopeDataUseCaseTest {
    private lateinit var gyroscopeSensor: GyroscopeSensor
    private lateinit var useCase: GetGyroscopeDataUseCase

    @Before
    fun setup() {
        // Declaración de Variables y Configuración Inicial
        gyroscopeSensor = mockk<GyroscopeSensor>(relaxed = true)
        useCase = GetGyroscopeDataUseCase(gyroscopeSensor)
    }

    @Test
    fun `test rotationY updates`() =
        runBlocking {
            // Mock StateFlow de StateFlow para rotaciónY
            val rotationYFlow = MutableStateFlow(0f)
            every { gyroscopeSensor.rotationY } returns rotationYFlow

            // Launch a coroutine para recopilar actualizaciones de rotaciónY
            val job =
                launch {
                    useCase.rotationY.collect { observedRotationY ->
                        assertTrue(observedRotationY > 1.5f) // Assert que la rotación Y es mayor que 1,5f
                    }
                }

            // Simular actualizaciones en gyroscopeSensor.rotationY
            rotationYFlow.value = 2.0f // Valor de ejemplo, mayor que el umbral

            // Simular otra actualización para probar más valores
            rotationYFlow.value = 0.5f // Valor de ejemplo, por debajo del umbral

            // Limpiar
            job.cancel()
        }
}

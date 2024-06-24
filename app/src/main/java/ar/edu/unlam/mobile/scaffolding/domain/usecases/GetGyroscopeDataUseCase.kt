package ar.edu.unlam.mobile.scaffolding.domain.usecases

import ar.edu.unlam.mobile.scaffolding.data.sensors.GyroscopeSensor
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetGyroscopeDataUseCase
    @Inject
    constructor(
        private val gyroscopeSensor: GyroscopeSensor,
    ) {
        val rotationX: StateFlow<Float> = gyroscopeSensor.rotationX
        val rotationY: StateFlow<Float> = gyroscopeSensor.rotationY

        fun unregisterListener() {
            gyroscopeSensor.unregisterListener()
        }
    }

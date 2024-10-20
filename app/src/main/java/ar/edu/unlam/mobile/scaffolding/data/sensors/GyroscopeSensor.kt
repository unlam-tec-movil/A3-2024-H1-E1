package ar.edu.unlam.mobile.scaffolding.data.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GyroscopeSensor(context: Context) : SensorEventListener {
    private val sensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private var gyroscopeSensor: Sensor? = null

    // Flujos de estado para rotación alrededor de los ejes X e Y
    private val _rotationX = MutableStateFlow(0f)
    val rotationX: StateFlow<Float> get() = _rotationX

    private val _rotationY = MutableStateFlow(0f)
    val rotationY: StateFlow<Float> get() = _rotationY

    init {
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        startListening()
    }

    private fun startListening() {
        gyroscopeSensor?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    private fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(
        sensor: Sensor?,
        accuracy: Int,
    ) {
        // Not needed for gyroscope
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_GYROSCOPE) {
                _rotationX.value = it.values[0]
                _rotationY.value = it.values[1]
            }
        }
    }

    fun unregisterListener() {
        stopListening()
    }

    fun registerListener() {
        startListening()
    }
}

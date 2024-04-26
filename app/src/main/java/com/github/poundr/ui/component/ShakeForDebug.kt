package com.github.poundr.ui.component

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import kotlin.math.abs

private const val SHAKE_THRESHOLD = 1200

@Composable
fun ShakeForDebug(
    onShake: () -> Unit
) {
    val context = LocalContext.current
    val sensorManager = remember(context) {
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
    val accelerometer = remember(sensorManager) {
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    DisposableEffect(accelerometer) {
        val listener = object : SensorEventListener {
            private var lastUpdate: Long = 0
            private var last_x: Float = 0.0f
            private var last_y: Float = 0.0f
            private var last_z: Float = 0.0f

            override fun onSensorChanged(event: SensorEvent) {
                val curTime = System.currentTimeMillis()
                if ((curTime - lastUpdate) > 100) {
                    val diffTime = (curTime - lastUpdate)
                    lastUpdate = curTime

                    val x = event.values[0]
                    val y = event.values[1]
                    val z = event.values[2]

                    val speed = abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000

                    if (speed > SHAKE_THRESHOLD) {
                        onShake()
                    }

                    last_x = x
                    last_y = y
                    last_z = z
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                // empty
            }
        }

        sensorManager.registerListener(listener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }
}
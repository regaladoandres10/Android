package com.example.futbolitopocket.ui.composable

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.futbolitopocket.model.Demo
import com.example.futbolitopocket.ui.Demo
import com.example.futbolitopocket.ui.NotAvailableDemo
import dev.ricknout.composesensors.accelerometer.isAccelerometerSensorAvailable
import dev.ricknout.composesensors.accelerometer.rememberAccelerometerSensorValueAsState

@Composable
fun FutbolitoScreen() {
    if (isAccelerometerSensorAvailable()) {
        //Esta leyendo el acelerometro del telefono
        val sensorValue by rememberAccelerometerSensorValueAsState()
        //x -> Inclinzacion izquierda y derecha
        //y -> Inclinación de arriba y abajo
        //z -> Profundidad
        val (x, y, z) = sensorValue.value
        Demo(
            demo = Demo.FUTBOLITOPOCKET,
            value = "X: $x m/s^2\nY: $y m/s^2\nZ: $z m/s^2",
        ) {
            val width = constraints.maxWidth.toFloat()
            val height = constraints.maxHeight.toFloat()
            var center by remember { mutableStateOf(Offset(width / 2, height / 2)) }
            var velocity by remember { mutableStateOf(Offset(0f, 0f)) }
            val orientation = LocalConfiguration.current.orientation
            //Color de la pelota
            val contentColor = LocalContentColor.current
            //Tamaño de la pelota
            val radius = with(LocalDensity.current) { 15.dp.toPx() }

            //Velocidad de la pelota con acelerometro
            velocity = Offset(
                x = velocity.x + x * 0.2f,
                y = velocity.y + y * 0.2f,
            )

            //Movimiento de la pelota
            center = Offset(
                x = center.x + velocity.x,
                y = center.y + velocity.y
            )

            //Rebotes
            // Izquierda
            if (center.x < radius) {
                velocity = Offset(-velocity.x, velocity.y)
                center = Offset(radius, center.y)
            }

            // Derecha
            if (center.x > width - radius) {
                velocity = Offset(-velocity.x, velocity.y)
                center = Offset(width - radius, center.y)
            }

            // Arriba
            if (center.y < radius) {
                velocity = Offset(velocity.x, -velocity.y)
                center = Offset(center.x, radius)
            }

            // Abajo
            if (center.y > height - radius) {
                velocity = Offset(velocity.x, -velocity.y)
                center = Offset(center.x, height - radius)
            }

            /*
            //Movimientos de la pelota
            center = if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                Offset(
                    //coerceIn(...) -> Hace que la pelota no salga de la pantalla
                    //Si movemos el celular en x se mueve hacia los lados la pelota
                    x = (center.x - x).coerceIn(radius, width - radius),
                    //Si inclinamos el celular en y se mueve de arriba abajo la pelota
                    y = (center.y + y).coerceIn(radius, height - radius),
                )
            } else {
                Offset(
                    x = (center.x + y).coerceIn(radius, width - radius),
                    y = (center.y + x).coerceIn(radius, height - radius),
                )
            }
             */

            //Campo de futbol con componentes
            Canvas(modifier = Modifier.fillMaxSize()) {

                //Pantalla verde
                drawRect(
                    color = Color.Green,
                    size = size
                )

                //Dibujar porteria de arriba
                val goalWidth = width * 0.4f //40% ancho
                val goalHeight = 40f //alto de la porteria

                drawRect(
                    color = Color.White,
                    topLeft = Offset(
                        //Poner la porteria centrada
                        x = (width - goalWidth) / 2,
                        //Pegar arriba
                        y = 0f
                    ),
                    size = Size(goalWidth, goalHeight)
                )

                //Dibujar porteria de abajo
                drawRect(
                    color = Color.White,
                    topLeft = Offset(
                        x = (width - goalWidth) / 2,
                        y = height - goalHeight
                    ),
                    size = Size(goalWidth, goalHeight)
                )

                //Dibujamos la pelota
                drawCircle(
                    color = contentColor,
                    radius = radius,
                    center = center,
                )
            }
        }
    } else {
        NotAvailableDemo(demo = Demo.FUTBOLITOPOCKET)
    }
}
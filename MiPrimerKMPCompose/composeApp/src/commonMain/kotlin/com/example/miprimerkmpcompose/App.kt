package com.example.miprimerkmpcompose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.IllegalTimeZoneException
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource

import miprimerkmpcompose.composeapp.generated.resources.Res
import miprimerkmpcompose.composeapp.generated.resources.compose_multiplatform
import kotlin.time.Clock

fun currentTimeAt(location: String) : String? {
    //Obtenemos la fecha
    fun LocalTime.formatted() = "$hour:$minute:$second"

    return try {
        //Tiempo de ahora
        val time = Clock.System.now()
        //Tiempo de zona
        val zone = TimeZone.of(location)
        //Convierte el tiempo a una fecha y hora local
        val localTime = time.toLocalDateTime(zone).time
        "The time in $location is ${localTime.formatted()}"
    } catch (ex: IllegalTimeZoneException) {
        null
    }
}

@Composable
@Preview
fun App() {
    MaterialTheme {
        //var showContent by remember { mutableStateOf(false) }
        //Localizacion
        var location by remember { mutableStateOf("Europe/Berlin") }

        //Tiempo de localización
        var timeAtLocation by remember { mutableStateOf("No location selected") }

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            //horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                timeAtLocation,
                style = TextStyle(fontSize = 20.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
            )
            //Modificar la localizacion
            TextField(
                value = location,
                onValueChange = { location = it },
                modifier = Modifier.padding(top = 10.dp)
            )
            Button(
                onClick = { timeAtLocation = currentTimeAt(location) ?: "Invalid Location" },
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text("Show time At Location")
            }
        }
    }

}


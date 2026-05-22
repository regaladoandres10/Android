package com.example.miprimerkmpcompose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import miprimerkmpcompose.composeapp.generated.resources.eg
import miprimerkmpcompose.composeapp.generated.resources.fr
import miprimerkmpcompose.composeapp.generated.resources.id
import miprimerkmpcompose.composeapp.generated.resources.jp
import miprimerkmpcompose.composeapp.generated.resources.mx
import org.jetbrains.compose.resources.DrawableResource
import kotlin.time.Clock

data class Country(val name: String, val zone: TimeZone, val image: DrawableResource)

fun currentTimeAt(location: String, zone: TimeZone) : String {
    //Obtenemos la fecha
    fun LocalTime.formatted() = "$hour:$minute:$second"

    //Tiempo de ahora
    val time = Clock.System.now()

    //Convierte el tiempo a una fecha y hora local
    val localTime = time.toLocalDateTime(zone).time

    return "The time in $location is ${localTime.formatted()}"
}

fun countries() = listOf(
    Country("Japan", TimeZone.of("Asia/Tokyo"), Res.drawable.jp),
    Country("France", TimeZone.of("Europe/Paris"), Res.drawable.fr),
    Country("Mexico", TimeZone.of("America/Mexico_City"), Res.drawable.mx),
    Country("Indonesia", TimeZone.of("Asia/Jakarta"), Res.drawable.id),
    Country("Egypt", TimeZone.of("Africa/Cairo"), Res.drawable.eg),
)

@Composable
@Preview
fun App( countries: List<Country> = countries() ) {
    MaterialTheme {
        //var showContent by remember { mutableStateOf(false) }
        //Lista de paises
        var showCountries by remember { mutableStateOf(false) }

        //Tiempo de localización
        var timeAtLocation by remember { mutableStateOf("No location selected") }

        Column(
            modifier = Modifier
                .padding(20.dp)
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
            Row(modifier = Modifier.padding(start = 20.dp, top = 10.dp)) {
                DropdownMenu(
                    expanded = showCountries,
                    onDismissRequest = { showCountries = false }
                ) {
                    countries().forEach { (name, zone, image) ->
                        //Menu desplegable
                        DropdownMenuItem(
                            text = { Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painterResource(image),
                                    modifier = Modifier.size(50.dp).padding(end = 10.dp),
                                    contentDescription = "$name flag"
                                )
                                Text(name)
                            } },
                            onClick = {
                                timeAtLocation = currentTimeAt(name, zone)
                                showCountries = false
                            }
                        )
                    }
                }
            }

            Button(modifier = Modifier.padding(start = 20.dp, top = 10.dp),
                onClick = { showCountries = !showCountries }) {
                Text("Select Location")
            }
        }
    }

}


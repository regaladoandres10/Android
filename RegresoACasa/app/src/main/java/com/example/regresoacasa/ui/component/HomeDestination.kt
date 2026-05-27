package com.example.regresoacasa.ui.component

import android.location.Geocoder
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.osmdroid.util.GeoPoint
import java.util.*

/*
 Localizaciones de prueba:
 Av Tecnológico, Querétaro, México

 Av Tecnológico 123, Santiago de Querétaro, Querétaro, México

 [Calle] [Número], [Colonia], [Ciudad], [Estado], México
 Leovino Zavala 15, Uriangato, Guanajuato, México
 5 de Mayo 120, Centro, Uriangato, Guanajuato, México
 Insurgentes 45, Uriangato, GTO, México
 Centro, Uriangato, Guanajuato

 */

@Composable
fun HomeDestinationSection(
    onDestinationSelected: (
        GeoPoint
    ) -> Unit
) {

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var address by remember { mutableStateOf("") }

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )

    ) {

        OutlinedTextField(
            value = address,
            onValueChange = { address = it },

            label = { Text("Dirección de tu casa") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Button(
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 80.dp),
            onClick = {
                scope.launch(Dispatchers.IO) {

                    try {
                        val geocoder =
                            Geocoder(
                                context,
                                Locale.getDefault()
                            )

                        val result =
                            geocoder
                                .getFromLocationName(
                                    address,
                                    1
                                )

                        //Verificar si se encuentran las coordenadas
                        val addressResult = result?.firstOrNull()

                        if (addressResult != null) {

                            withContext(Dispatchers.IO){
                                println("CASA -> ${addressResult.latitude}, ${addressResult.longitude}")

                                onDestinationSelected(
                                    GeoPoint(
                                        addressResult.latitude,
                                        addressResult.longitude
                                    )
                                )
                            }


                        } else {
                            println("NO ENCONTRO DIRECCION")
                        }

                    } catch (_: Exception) {

                    }

                }



            }

        ) {
            Text("Buscar casa")
        }

    }

}
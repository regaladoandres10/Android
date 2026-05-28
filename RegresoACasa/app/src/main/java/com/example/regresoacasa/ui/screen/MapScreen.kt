package com.example.regresoacasa.ui.screen

import android.Manifest
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.compose.ui.platform.LocalContext
import com.example.regresoacasa.data.network.RouteService
import com.google.android.gms.location.Priority
import com.example.regresoacasa.ui.component.HomeDestinationSection
import com.example.regresoacasa.ui.component.PermissionBox
import com.google.android.gms.location.LocationServices
import org.osmdroid.util.GeoPoint
import com.utsman.osmandcompose.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/*
   ubicacion uriangato
            GeoPoint(
                20.1371,
                -101.1779
 */

@Composable
fun MapScreen() {

    PermissionBox(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ),

        requiredPermissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    ) {
        MapContent()
    }
}

//Verificar que detecete el GPS sin verificar permisos
@SuppressLint("MissingPermission")
@Composable
fun MapContent() {

    val context = LocalContext.current

    //Ubicacion actual o por defecto
    var location by remember {
        mutableStateOf<GeoPoint?>(
            null
        )
    }

    //Localizacion de la casa
    var homeLocation by remember {
        mutableStateOf<GeoPoint?>(null)
    }

    //Lista de puntos de la ruta
    var points by remember {

        mutableStateOf(
            emptyList<GeoPoint>()
        )

    }

    //Cliente de GPS de google
    val client =
        remember {
            LocationServices
                .getFusedLocationProviderClient(
                    context
                )
        }

    //Estado de la camara del mapa
    val cameraState =
        rememberCameraState {
            geoPoint = GeoPoint(
                20.5888,
                -100.3899
            )
            zoom = 16.0
        }

    LaunchedEffect(Unit) {

        //Obtener la ubicacion actual
        val result =
            client.getCurrentLocation(
                //Alta precision de ubicacion
                Priority.PRIORITY_HIGH_ACCURACY,
                null
            )

        result.addOnSuccessListener {
            if (it != null) {
                //Guardar la localizacion
                location =
                    //Convierte en coordenadas a GeoPoint
                    GeoPoint(
                        it.latitude,
                        it.longitude
                    )

                //Mover la camara
                cameraState.geoPoint = GeoPoint(
                    it.latitude,
                    it.longitude
                )
                cameraState.zoom = 16.0

                println("CURRENT LOCATION -> ${it.latitude}, ${it.longitude}")

            }

        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val scope = rememberCoroutineScope()

        //Buscador de la direccion
        HomeDestinationSection {
            //Guardar la ubicacio de la casa
            homeLocation = it
            //Mover mapa a la direccion
            cameraState.geoPoint = it
            //Aleja el zoom
            cameraState.zoom = 15.0

            scope.launch(Dispatchers.IO) {

                try {
                    //Peticion a OpenRouterService
                    val result =
                        RouteService
                            .api
                            .getDirections(
                                //Rutas para autos
                                profile = "driving-car",
                                //Ubicacion actual
                                start = "${location?.longitude}," + "${location?.latitude}",
                                //Destino (direccion destino)
                                end = "${it.longitude}," + "${it.latitude}"
                            )

                    println("Result: ${result}")
                    println( "Result size:  ${result.features.size}")
                    points =
                        //Respuesta de GeoJSON
                        result
                            .features
                            //Ruta encontrada
                            .first()
                            //Lista de coordenadas
                            .geometry
                            .coordinates
                            //Transformamos en coordenadas
                            .map {
                                GeoPoint(
                                    it[1],
                                    it[0]
                                )
                            }
                    withContext(
                        Dispatchers.Main
                    ) {

                        points = points

                    }
                }
                catch (e: Exception) {
                    e.printStackTrace()
                    println("ERROR ORS -> ${e.message}")
                }

            }

        }

        //Mapa
        OpenStreetMap(
            modifier = Modifier
                .weight(0.2f),
            cameraState = cameraState
        ) {

            //Ubicacion del dispositivo con el marker
            location?.let { currentLocation ->
                val currentMarkerState =
                    rememberMarkerState()
                //Actualizamos el marker con la ubicacion actual
                LaunchedEffect(currentLocation) {
                    currentMarkerState.geoPoint = currentLocation
                }
                Marker(
                    state = currentMarkerState,
                    title = "Ubicación actual"
                )
            }

            //Ubicacion de la direccion de la casa con el marker
            homeLocation?.let { home ->
                val homeMarkerState =
                    rememberMarkerState()

                //Actualizar el marker cada recomponsición
                LaunchedEffect(home) {
                    homeMarkerState.geoPoint = home
                }

                Marker(
                    state = homeMarkerState,
                    title = "Mi casa"
                )

            }

            //Dibujar trazo de la ruta
            if (points.isNotEmpty()) {
                Polyline(geoPoints = points)

            }

        }

    }
}
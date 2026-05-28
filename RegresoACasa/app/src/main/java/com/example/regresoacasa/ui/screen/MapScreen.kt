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


@SuppressLint("MissingPermission")
@Composable
fun MapContent() {

    val context = LocalContext.current

    //Localizacion actual o por defecto
    var location by remember {
        mutableStateOf<GeoPoint?>(
            null
        )
    }

    //Localizacion de la casa
    var homeLocation by remember {
        mutableStateOf<GeoPoint?>(null)
    }

    //Estada para la ruta
    var points by remember {

        mutableStateOf(
            emptyList<GeoPoint>()
        )

    }

    val client =
        remember {
            LocationServices
                .getFusedLocationProviderClient(
                    context
                )
        }

    val cameraState =
        rememberCameraState {
            geoPoint = GeoPoint(
                20.5888,
                -100.3899
            )
            zoom = 16.0
        }

    LaunchedEffect(Unit) {

        //Ubicación real
        val result =

            client.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                null
            )

        result.addOnSuccessListener {
            if (it != null) {
                location =
                    GeoPoint(
                        it.latitude,
                        it.longitude
                    )

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

        HomeDestinationSection {
            homeLocation = it
            cameraState.geoPoint = it
            cameraState.zoom = 15.0

            scope.launch(Dispatchers.IO) {

                try {
                    val result =
                        RouteService
                            .api
                            .getDirections(
                                profile = "driving-car",
                                start = "${location?.longitude}," + "${location?.latitude}",

                                end = "${it.longitude}," + "${it.latitude}"
                            )

                    println("Result: ${result}")
                    println( "Result size:  ${result.features.size}")
                    points =
                        result
                            .features
                            .first()
                            .geometry
                            .coordinates
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

        OpenStreetMap(
            modifier = Modifier
                .weight(0.2f),
            cameraState = cameraState
        ) {

            //Ubicacion actual en el marker
            location?.let { currentLocation ->
                Marker(
                    state =
                        rememberMarkerState(
                            geoPoint =
                                currentLocation
                        ),
                    title = "Ubicación actual"
                )

            }

            homeLocation?.let { home ->
                val homeMarkerState =
                    rememberMarkerState()

                LaunchedEffect(home) {
                    //Actualizar el marker cada recomponsición
                    homeMarkerState.geoPoint =
                        home
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
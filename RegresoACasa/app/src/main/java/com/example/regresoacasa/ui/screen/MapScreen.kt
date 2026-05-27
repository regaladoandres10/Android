package com.example.regresoacasa.ui.screen

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.compose.ui.platform.LocalContext
import com.example.regresoacasa.ui.component.HomeDestinationSection
import com.example.regresoacasa.ui.component.PermissionBox
import com.google.android.gms.location.LocationServices
import org.osmdroid.util.GeoPoint
import com.utsman.osmandcompose.*

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

    var location by remember {
        mutableStateOf(
            GeoPoint(
                20.5888,
                -100.3899
            )
        )
    }

    var homeLocation by remember {
        mutableStateOf<GeoPoint?>(null)
    }

    val client =
        remember {
            LocationServices
                .getFusedLocationProviderClient(
                    context
                )
        }

    LaunchedEffect(Unit) {

        val result = client.lastLocation

        result.addOnSuccessListener {
            if (it != null) {

                location =
                    GeoPoint(
                        it.latitude,
                        it.longitude
                    )

            }

        }
    }

    val cameraState =
        rememberCameraState {
            geoPoint = location
            zoom = 16.0
        }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        HomeDestinationSection {
            homeLocation = it
            //Mover automaticamente el mapa
            cameraState.geoPoint = it
            cameraState.zoom = 17.0
        }

        OpenStreetMap(
            modifier = Modifier
                .weight(0.2f),
            cameraState = cameraState
        ) {

            Marker(
                state = rememberMarkerState(geoPoint = location),
                title = "Estoy aquí"
            )

            homeLocation?.let { home ->
                Marker(
                    state = rememberMarkerState(geoPoint = home),
                    title = "Mi casa"
                )

            }
        }

    }
}
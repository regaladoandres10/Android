package com.example.pushnotificationdemo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.compose.material3.Text
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : ComponentActivity() {

    //Obtener token del dispositivo
    override fun onStart() {
        super.onStart()
        //Solicita a Firebase el token del dispositivo
        FirebaseMessaging.getInstance().token
            //Escucha cuando la solicitud termina
            .addOnCompleteListener {

                //Verificamos si hay un error al momento de obtener el token
                if (!it.isSuccessful) {
                    Log.e(
                        "FCM",
                        "Error obteniendo token",
                        it.exception
                    )
                    return@addOnCompleteListener
                }

                //Guardamos el token
                val token = it.result

                Log.d("FCM", token)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Revisamos si ya el usuario dio permiso para mostrar notificaciones
        if (
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            //Mostramos el cuadro de dialogo para solicitar permiso
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.POST_NOTIFICATIONS
                ),
                100
            )
        }

        enableEdgeToEdge()
        setContent {
            Text("Firebase notification")
        }
    }
}


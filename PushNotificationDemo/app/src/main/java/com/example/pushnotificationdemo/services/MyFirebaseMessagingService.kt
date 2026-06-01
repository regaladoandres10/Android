package com.example.pushnotificationdemo.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/**
 * Servicio de Firebase que permite a tu aplicación recibir y mostrar notificaciones push
 */

//Clase que hereda de FirebaseMessagingService
class MyFirebaseMessagingService : FirebaseMessagingService() {

    //Obtiene el token unico del dispositivo
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d("FCM", "Token: $token")
    }

    //Recibe mensajes enviados por Firebase
    @RequiresApi(Build.VERSION_CODES.O)
    //Se activa cuando llega un mensaje de Firebase mientras la app esta abierta
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        //Extraemos el titulo y el cuerpo del mensaje
        val titulo = message.notification?.title ?: ""
        val cuerpo = message.notification?.body ?: ""

        //Mostrar la notificación
        mostrarNotificacion(titulo, cuerpo)
    }

    //Mostrar una notificación en Android o creación de notificacion visual
    @RequiresApi(Build.VERSION_CODES.O)
    private fun mostrarNotificacion(
        titulo: String,
        mensaje: String
    ) {

        val channelId = "FCM_CHANNEL"

        //Obtiene el servicio de notificaciones para gestionar notificaiciones en el sistema
        val notificationManager =
            getSystemService(NotificationManager::class.java)

        //Configuramos el canal de notificaciones
        val channel = NotificationChannel(
            channelId,
            "Mensajes Push",
            NotificationManager.IMPORTANCE_HIGH
        )

        //Registramos el canal en el sistema
        notificationManager.createNotificationChannel(channel)

        //Construimos la notificación
        val notification =
            NotificationCompat.Builder(this, channelId)
                //Asiganamos el titulo
                .setContentTitle(titulo)
                //Asignamos el mensaje
                .setContentText(mensaje)
                //Definimos un icono
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .build()

        //Mostrar la notificación en el sistema para que el usuario lo vea
        notificationManager.notify(
            //Crear un ID único para la notificación, en caso de que lleguen varias se haga una lista
            System.currentTimeMillis().toInt(),
            notification
        )
    }
}
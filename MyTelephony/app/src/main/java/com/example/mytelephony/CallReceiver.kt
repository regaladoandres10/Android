package com.example.mytelephony

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.util.Log

class CallReceiver: BroadcastReceiver() {

    //Usamos un companion object para que el estado sobreviva si el receiver
    companion object {
        //Crear un estado para saber si ya he enviado un mensaje
        var alreadySent = false
    }



    //Metodo principal para recibir eventos en el BroadcastReceiver
    override fun onReceive(context: Context, intent: Intent) {

        //Creamos instancia del SharedPreferences
        val prefs = context.getSharedPreferences(
            "settings",
            Context.MODE_PRIVATE
        )

        //El archivo se guarda en la siguiente ruta: data/data/com.example.mytelephony/shared_prefs/settings.xml

        //Saber si la respuesta automatica esta activada
        val enabled = prefs.getBoolean("autoReplyEnabled", false)


        //obtener/leer numero en SharedPreferences
        val savedNumber = prefs.getString("savedNumber", "") ?: ""
        //obtener/leer mensaje en SharedPreferences
        val savedMessage = prefs.getString("savedMessage", "") ?: ""

        //Validar el evento del sistema
        if (intent.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            //Verificar que estan llamando con el estado
            val state: String? = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            //Numero destino
            val incomingNumber: String? = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

            //Detectar si estan llamando y verificar que el numero entrante lo tenga guardado el usuario
            if (enabled && state == TelephonyManager.EXTRA_STATE_RINGING && !alreadySent && incomingNumber != null && incomingNumber == savedNumber) {
                //Obtener la instancia de smsManager
                val smsManager = context.getSystemService(SmsManager::class.java)
                //Enviar mensaje
                smsManager.sendTextMessage(
                    incomingNumber, //Numero destino
                    null,
                    savedMessage, //Mensaje que se envia
                    null,
                    null,
                )
                //Marcar que ya envie el mensaje
                alreadySent = true
                Log.d("CallReceiver", "Mensaje enviado a ${incomingNumber}")
            }
            if (state == TelephonyManager.EXTRA_STATE_IDLE) { //Si la llamada ha finalizado
                //Aun no se ha enviado el mensaje resetear
                alreadySent = false

            }
        }


    }

}
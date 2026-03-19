package com.example.mytelephony

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsManager
import android.telephony.TelephonyManager

class CallReceiver: BroadcastReceiver() {

    //Crear un estado para saber si ya he enviado un mensaje
    var alreadySent = false

    //Metodo principal para recibir eventos en el BroadcastReceiver
    override fun onReceive(context: Context, intent: Intent) {

        //Numero guardado
        val savedNumber = "445434343"
        //Mensaje
        val message = "Hola"

        //Validar el evento del sistema
        if (intent.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            //Verificar que estan llamando con el estado
            val state: String? = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            //Numero destino
            val incomingNumber: String? = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

            //Detectar si estan llamando y verificar que el numero entrante lo tenga guardado el usuario
            if (state == TelephonyManager.EXTRA_STATE_RINGING && !alreadySent && incomingNumber == savedNumber) {
                //Obtener la instancia de smsManager
                val smsManager = SmsManager.getDefault()
                //Enviar mensaje
                smsManager.sendTextMessage(
                    incomingNumber, //Numero destino
                    null,
                    message, //Mensaje que se envia
                    null,
                    null,
                )
                //Marcar que ya envie el mensaje
                alreadySent = true
            }
            if (state == TelephonyManager.EXTRA_STATE_IDLE) { //Si la llamada ha finalizado
                //Aun no se ha enviado el mensaje resetear
                alreadySent = false
            }
        }


    }

}
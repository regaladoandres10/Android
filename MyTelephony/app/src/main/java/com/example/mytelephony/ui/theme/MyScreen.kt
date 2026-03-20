package com.example.mytelephony.ui.theme

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun MyScreen() {
    val context = LocalContext.current
    //Crear instancia de SharedPreferences
    val prefs = context.getSharedPreferences(
        "settings",
        Context.MODE_PRIVATE
    )

    //Obteniendo los datos del numero y mensaje de SharedPreferences
    val savedNumber = prefs.getString("savedNumber", "") ?: ""
    val savedMessage = prefs.getString("savedMessage", "") ?: ""

    var number by remember { mutableStateOf(savedNumber) }
    var message by remember { mutableStateOf(savedMessage) }

    //Solicitar el permiso al usuario
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        //Se ejecuta cuando el usuario responde a los permisos
        val phoneGranted = permissions[Manifest.permission.READ_PHONE_STATE] == true
        val callLogGranted = permissions[Manifest.permission.READ_CALL_LOG] == true
        val smsGranted = permissions[Manifest.permission.SEND_SMS] == true

        //Si ambos permisos fueron aceptados por el usuario
        if (phoneGranted && callLogGranted && smsGranted) {
            //Guardamos el valor
            prefs.edit().putBoolean("autoReplyEnabled", true).apply()
            Toast.makeText(context, "Respuesta automática activada", Toast.LENGTH_SHORT).show()
        } else {
            prefs.edit().putBoolean("autoReplyEnabled", false).apply()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = number,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            onValueChange = { newNumber ->
                number = newNumber
            },
            label = { Text("Numero") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = message,
            onValueChange = { newMessage ->
                message = newMessage
            },
            label = { Text("Mensaje") }
        )

        Spacer(modifier = Modifier.height(12.dp))


        //Boton para guardar los datos de forma interna en el telefono
        Button(
            onClick = {
                if (number.isNotBlank() && message.isNotBlank()) {
                    //Guardar el numero en el telefono de forma interna
                    prefs.edit().putString("savedNumber", number).apply()
                    prefs.edit().putString("savedMessage", message).apply()

                    //Mostrar notificacion de que los datos sean guardado correctamente
                    Toast.makeText(context, "Datos guardados", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Completa los campos", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text("Guardar datos")
        }

        Spacer(modifier = Modifier.height(12.dp))

        //Boton para guardar permisos
        Button(
            onClick = {
                permissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.SEND_SMS
                    )
                )
            }
        ) {
            Text("Activar respuesta automaticamente")
        }
    }




}
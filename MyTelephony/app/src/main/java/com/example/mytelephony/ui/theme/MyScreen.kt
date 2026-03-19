package com.example.mytelephony.ui.theme

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import android.Manifest

@Composable
fun MyScreen() {
    //Solicitar el permiso al usuario
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        //Se ejecuta cuando el usuario responde a los permisos
        val phoneGranted = permissions[Manifest.permission.READ_PHONE_STATE] == true
        val smsGranted = permissions[Manifest.permission.SEND_SMS] == true

        //Si ambos permisos fueron aceptados por el usuario
        if (phoneGranted && smsGranted) {
            //Mostrar al usuario que ya ha concedido los permisos
        }
    }

    Button(
        onClick = {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.SEND_SMS
                )
            )
        }
    ) {
        Text("Activar respuesta automatica")
    }

}
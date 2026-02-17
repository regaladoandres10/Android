package com.example.appsice.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.appsice.viewmodel.SNUiState
import com.example.appsice.viewmodel.SNViewModel

@Composable
fun ScreenLogin(
    snViewModel: SNViewModel,
    snUiState: SNUiState,
    onLoginSuccesed: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    var text by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Campo matricula
        TextField(
            value = text,
            onValueChange = { newText ->
                text = newText
            },
            label = { Text("Matricula") },
            singleLine = true,
            modifier = Modifier.
            padding(16.dp).
            fillMaxWidth()
        )
        //Campo contra
        TextField(
            value = password,
            onValueChange = { newPassword ->
                password = newPassword
            },
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            label = { Text("Contraseña") },
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                // Please provide localized description for accessibility services
                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = {passwordVisible = !passwordVisible}){
                    Icon(imageVector  = image, description)
                }
            },
            modifier = Modifier.padding(16.dp).fillMaxWidth()
        )
        //Boton de inciar sesión
        Button(
            onClick = {
                //Conectarnos a la api
                snViewModel.accesoSN(text, password)
            },
            modifier = Modifier.fillMaxWidth().padding(20.dp)
        ) {
            Text(text = "Iniciar sesión")
        }

        LaunchedEffect(snUiState) {
            if (snUiState is SNUiState.Success) {
                Log.d("navigacion", "Navegando a profile")
                //Cambiar de pantalla
                onLoginSuccesed()
            }
        }

        when (snUiState) {
            is SNUiState.Loading -> CircularProgressIndicator()
            is SNUiState.Error -> Text("Error al iniciar sesión")
            is SNUiState.Success -> Text("Sesión iniciada correctamente")
        }
    }
}
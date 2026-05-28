package ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions

//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Visibility
//import androidx.compose.material.icons.filled.VisibilityOff

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

//import ui.viewmodel.SNViewModel
import ui.viewmodel.SNUiState
import ui.viewmodel.SNViewModel

@Composable
fun ScreenLogin(
    viewModel: SNViewModel,
    uiState: SNUiState,
    onLoginSuccess: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {

    // Matrícula
    var matricula by rememberSaveable { mutableStateOf("S21120230") }

    // Contraseña
    var password by rememberSaveable { mutableStateOf("Tc4_b2=") }

    // Mostrar contraseña
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    // Navegar cuando termine el login
    LaunchedEffect(uiState) {
        if (uiState is SNUiState.Success) {
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Campo matrícula
        TextField(
            value = matricula,
            onValueChange = { matricula = it },
            label = { Text("Matrícula") },
            singleLine = true,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )

        //Campo contraseña
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation =
                if (passwordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),

            trailingIcon = {
                IconButton(
                    onClick = {
                        passwordVisible = !passwordVisible
                    }
                ) {
                    /*
                    Icon(
                        imageVector =
                            if (passwordVisible) {
                                Icons.Default.Visibility
                            } else {
                                Icons.Default.VisibilityOff
                            },
                        contentDescription = null
                    )

                     */
                }
            },

            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )

        //Botón login
        Button(
            onClick = {
                viewModel.login(
                    matricula,
                    password
                )

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(text = "Iniciar sesión")
        }

        //Estado visual
        when (uiState) {
            SNUiState.Loading -> {
                CircularProgressIndicator()
            }
            is SNUiState.Error -> {
                Text(
                    (uiState as SNUiState.Error).message
                )
            }
            SNUiState.Success -> {
                Text("Login correcto")
            }
        }
    }
}
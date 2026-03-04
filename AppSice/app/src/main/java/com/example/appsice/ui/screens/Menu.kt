package com.example.appsice.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MenuScreen(
    onPerfilClick: () -> Unit,
    onCargaClick: () -> Unit,
    onCardexClick: () -> Unit,
    onCaliUnidadClick: () -> Unit,
    onCaliFinalClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            onClick = onPerfilClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("PERFIL")
        }

        Button(
            onClick = onCargaClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("CARGA ACADÉMICA")
        }

        Button(
            onClick = onCardexClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("CARDEX")
        }

        Button(
            onClick = onCaliUnidadClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("CALIFICACIONES UNIDAD")
        }

        Button(
            onClick = onCaliFinalClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("CALIFICACIONES FINALES")
        }

        }
    }
package com.example.appsice.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.appsice.model.ProfileStudent
import kotlinx.serialization.InternalSerializationApi

@OptIn(InternalSerializationApi::class)
@Composable
fun ScreenProfile(
    profile: ProfileStudent,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        //horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = profile.nombre ?: "",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("Matrícula: ${profile.matricula}")
        Text("Carrera: ${profile.carrera}")
        Text("Especialidad: ${profile.especialidad}")
        Text("Semestre actual: ${profile.semActual}")
        Text("Créditos acumulados: ${profile.cdtosAcumulados}")
        Text("Créditos actuales: ${profile.cdtosActuales}")
        Text("Estatus: ${profile.estatus}")

        AsyncImage(
            model = "https://sicenet.surguanajuato.tecnm.mx/fotos/${profile.urlFoto}",
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
        )

    }
}
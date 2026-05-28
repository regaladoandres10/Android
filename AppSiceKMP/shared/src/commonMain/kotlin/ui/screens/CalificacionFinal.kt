@file:OptIn(InternalSerializationApi::class)

package ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.remote.model.CalificacionFinal
//import data.remote.model.CalificacionFinal
import kotlinx.serialization.InternalSerializationApi

@Composable
fun ScreenCalificacionFinal(
    calificaciones: List<CalificacionFinal>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(calificaciones) { calificacion ->
            CalificacionFinalCard(calificacion)
        }
    }
}


@Composable
private fun CalificacionFinalCard(
    cali: CalificacionFinal
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            cali.materia?.let {
                Text(
                    text = it,

                    style =
                        MaterialTheme
                            .typography
                            .titleMedium
                )
            }

            Spacer(Modifier.height(8.dp))

            Info(
                "Grupo",
                cali.grupo
            )

            Info(
                "Calificación final",
                cali.calif
            )

            Info(
                "Acreditación",
                cali.acred
            )


            if (cali.observaciones?.isNotBlank() ?: false) {

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Observaciones: ${cali.observaciones}",
                    style = MaterialTheme
                            .typography
                            .bodySmall
                )

            }

        }

    }

}


@Composable
private fun Info(
    title: String,
    value: Any?
) {
    Text(text = "$title: ${value ?: "-"}")
}
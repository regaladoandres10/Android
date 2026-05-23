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
import data.remote.model.CalificacionUnidad
import kotlinx.serialization.InternalSerializationApi

@Composable
fun ScreenCalificacionUnidad(
    calificaciones: List<CalificacionUnidad>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(calificaciones) { calificacion ->
            CalificacionUnidadCard(calificacion)
        }
    }
}

@Composable
private fun CalificacionUnidadCard(
    cali: CalificacionUnidad
) {

    Card(modifier = Modifier.fillMaxWidth(),
        elevation =
            CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)
        ) {
            cali.materia?.let {
                Text(text = it,

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

            Spacer(Modifier.height(12.dp))

            Text(
                text = "Calificaciones por unidad",
                style =
                    MaterialTheme
                        .typography
                        .labelMedium
            )

            Unidad(
                1,
                cali.c1
            )

            Unidad(
                2,
                cali.c2
            )

            Unidad(
                3,
                cali.c3
            )

            Unidad(
                4,
                cali.c4
            )

            Unidad(
                5,
                cali.c5
            )

            Unidad(
                6,
                cali.c6
            )

            Unidad(
                7,
                cali.c7
            )

            Unidad(
                8,
                cali.c8
            )

            Unidad(
                9,
                cali.c9
            )

            Unidad(
                10,
                cali.c10
            )

            Unidad(
                11,
                cali.c11
            )

            Unidad(
                12,
                cali.c12
            )

            Unidad(
                13,
                cali.c13
            )


            Spacer(
                Modifier.height(
                    8.dp
                )
            )

            Info(
                "Observaciones",
                cali.observaciones
            )

        }

    }

}



@Composable
private fun Unidad(
    numero: Int,
    valor: Any?
) {
    if (valor != null && valor.toString().isNotBlank()) {
        Text(text = "Unidad $numero: $valor")
    }
}


@Composable
private fun Info(
    title: String,
    value: Any?
) {
    Text(text = "$title: ${value ?: "-"}")
}
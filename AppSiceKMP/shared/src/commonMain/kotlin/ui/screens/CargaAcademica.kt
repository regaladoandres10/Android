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
import data.remote.model.CargaAcademica
import kotlinx.serialization.InternalSerializationApi

@OptIn(InternalSerializationApi::class)
@Composable
fun ScreenCargaAcademica(
    cargas: List<CargaAcademica>
) {
    LazyColumn(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),

        verticalArrangement =
            Arrangement.spacedBy(
                12.dp
            )
    ) {
        items(
            cargas
        ) { carga ->
            CargaCard(carga)
        }
    }
}



@Composable
private fun CargaCard( carga: CargaAcademica) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth(),
        elevation =
            CardDefaults
                .cardElevation(
                    4.dp
                )
    ) {
        Column(
            modifier =
                Modifier
                    .padding(16.dp)
        ) {
            carga.materia?.let {
                Text(
                    text = it,
                    style = MaterialTheme
                        .typography
                        .titleMedium
                )
            }

            Spacer(Modifier.height(8.dp))

            Info(
                "Grupo",
                carga.grupo
            )

            Info(
                "Docente",
                carga.docente
            )

            Info(
                "Créditos",
                carga.creditosMateria
            )


            Spacer(
                Modifier.height(
                    12.dp
                )
            )

            Text(text = "Horario",
                style =
                    MaterialTheme
                        .typography
                        .labelMedium

            )

            Info(
                "Lunes",
                carga.lunes
            )

            Info(
                "Martes",
                carga.martes
            )

            Info(
                "Miércoles",
                carga.miercoles
            )

            Info(
                "Jueves",
                carga.jueves
            )

            Info(
                "Viernes",
                carga.viernes
            )

            Info(
                "Sábado",
                carga.sabado
            )

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
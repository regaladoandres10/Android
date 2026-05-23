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
import data.remote.model.Cardex
import kotlinx.serialization.InternalSerializationApi

@OptIn(InternalSerializationApi::class)
@Composable
fun ScreenCardex(cardexList: List<Cardex>) {
    LazyColumn(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),

        verticalArrangement =
            Arrangement.spacedBy(12.dp)
    ) {
        items(cardexList) { materia ->
            CardexCard(materia)
        }
    }
}


@Composable
private fun CardexCard(materia: Cardex) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            materia.materia?.let {
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
                "Clave",
                materia.clvMat
            )

            Info(
                "Créditos",
                materia.cdts
            )

            Info(
                "Calificación",
                materia.calif
            )

            Info(
                "Acreditada",
                materia.acred
            )


            Spacer(
                Modifier.height(
                    12.dp
                )
            )


            Text(
                text = "Evaluaciones",

                style =
                    MaterialTheme
                        .typography
                        .labelMedium
            )


            Info(
                "S1",
                materia.s1
            )

            Info(
                "P1",
                materia.p1
            )

            Info(
                "A1",
                materia.a1
            )


            if (!materia.s2.isNullOrBlank()) {
                Info(
                    "S2",
                    materia.s2
                )
            }

            if (!materia.p2.isNullOrBlank()) {
                Info(
                    "P2",
                    materia.p2
                )
            }


            if (!materia.a2.isNullOrBlank()) {
                Info(
                    "A2",
                    materia.a2
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
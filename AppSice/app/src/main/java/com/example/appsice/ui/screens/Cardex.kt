package com.example.appsice.ui.screens

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
import com.example.appsice.data.local.entity.CardexEntity

@Composable
fun ScreenCardex(
    cardexList: List<CardexEntity>
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        items(cardexList) { materia ->

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = materia.materia,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text("Clave: ${materia.clvMat}")

                    Text("Créditos: ${materia.cdts}")

                    Text("Calificación: ${materia.calif}")

                    Text("Acreditada: ${materia.acred}")

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Evaluaciones",
                        style = MaterialTheme.typography.labelMedium
                    )

                    Text("S1: ${materia.s1}")
                    Text("P1: ${materia.p1}")
                    Text("A1: ${materia.a1}")

                    if (!materia.s2.isNullOrEmpty())
                        Text("S2: ${materia.s2}")

                    if (!materia.p2.isNullOrEmpty())
                        Text("P2: ${materia.p2}")

                    if (!materia.a2.isNullOrEmpty())
                        Text("A2: ${materia.a2}")

                }
            }
        }
    }
}
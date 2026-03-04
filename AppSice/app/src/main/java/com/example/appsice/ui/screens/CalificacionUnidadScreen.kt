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
import com.example.appsice.data.local.entity.CalificacionUnidadEntity

@Composable
fun ScreenCalificacionUnidad(
    calificaciones: List<CalificacionUnidadEntity>
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        items(calificaciones) { cali ->

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = cali.materia,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text("Grupo: ${cali.grupo}")

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Calificaciones por unidad",
                        style = MaterialTheme.typography.labelMedium
                    )

                    Text("Unidad 1: ${cali.c1}")
                    Text("Unidad 2: ${cali.c2}")
                    Text("Unidad 3: ${cali.c3}")
                    Text("Unidad 4: ${cali.c4}")
                    Text("Unidad 5: ${cali.c5}")
                    Text("Unidad 6: ${cali.c6}")
                    Text("Unidad 7: ${cali.c7}")
                    Text("Unidad 8: ${cali.c8}")
                    Text("Unidad 9: ${cali.c9}")
                    Text("Unidad 10: ${cali.c10}")
                    Text("Unidad 11: ${cali.c11}")
                    Text("Unidad 12: ${cali.c12}")
                    Text("Unidad 13: ${cali.c13}")

                    Spacer(modifier = Modifier.height(6.dp))

                    Text("Observaciones: ${cali.observaciones}")
                }
            }
        }
    }
}
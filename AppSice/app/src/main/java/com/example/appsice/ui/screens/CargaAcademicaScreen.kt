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
import com.example.appsice.data.local.entity.CargaAcademicaEntity

@Composable
fun ScreenCargaAcademica(
    cargas: List<CargaAcademicaEntity>
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        items(cargas) { carga ->

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = carga.materia,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text("Grupo: ${carga.grupo}")

                    Text("Docente: ${carga.docente}")

                    Text("Créditos: ${carga.creditosMateria}")

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Horario:",
                        style = MaterialTheme.typography.labelMedium
                    )

                    Text("Lunes: ${carga.lunes}")
                    Text("Martes: ${carga.martes}")
                    Text("Miércoles: ${carga.miercoles}")
                    Text("Jueves: ${carga.jueves}")
                    Text("Viernes: ${carga.viernes}")
                    Text("Sábado: ${carga.sabado}")

                }
            }
        }
    }
}
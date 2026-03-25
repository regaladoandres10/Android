package com.example.sicecustomer.ui.screens

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.sicecustomer.providers.AppSiceContract

@Composable
fun CargaAcademicaClientScreen() {

    val context = LocalContext.current
    val lista = remember { mutableStateListOf<String>() }

    fun cargarDatos() {
        lista.clear()

        try {
            //Resultado de la consulta
            val cursor = context.contentResolver.query(
                AppSiceContract.CargaAcademicaContract.CONTENT_URI_CARGA_ACADEMICA,
                null,
                null,
                null,
                null
            )

            cursor?.use {
                while (it.moveToNext()) {

                    val materia = it.getString(
                        it.getColumnIndexOrThrow("materia")
                    )

                    val docente = it.getString(
                        it.getColumnIndexOrThrow("docente")
                    )

                    val grupo = it.getString(
                        it.getColumnIndexOrThrow("grupo")
                    )

                    // 👇 más completo que cardex
                    lista.add("$materia - $docente (Grupo: $grupo)")
                }
            }

            Log.d("CURSOR_CARGA", "$cursor")

        } catch (e: Exception) {
            lista.add("Error: ${e.message}")
        }
    }

    //Se ejecuta al iniciar
    LaunchedEffect(Unit) {
        cargarDatos()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Carga Académica",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { cargarDatos() }) {
            Text("Recargar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {

            val values = ContentValues().apply {
                put("claveOficial", "CA999")
                put("materia", "Nueva Materia")
                put("docente", "Profe Demo")
                put("grupo", "A1")
                put("creditosMateria", 5)
            }

            try {
                context.contentResolver.insert(
                    AppSiceContract.CargaAcademicaContract.CONTENT_URI_CARGA_ACADEMICA,
                    values
                )

                //Recargar después de insertar
                cargarDatos()

            } catch (e: Exception) {
                lista.add("Error insert: ${e.message}")
                Log.e("ERROR_INSERT_CARGA", "${e.message}")
            }

        }) {
            Text("Insertar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(lista) { item ->
                Text(
                    text = item,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        Text("Cantidad: ${lista.size}")
    }
}
package com.example.sicecustomer

import android.content.ContentValues
import android.net.Uri
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
fun CardexClientScreen() {

    val context = LocalContext.current
    val lista = remember { mutableStateListOf<String>() }

    fun cargarDatos() {
        lista.clear()

        try {
            val cursor = context.contentResolver.query(
                AppSiceContract.CardexContract.CONTENT_URI_CARDEX,
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
                    lista.add(materia)
                }
            }
            Log.d("CURSOR", "$cursor")

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
            text = "Cardex",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { cargarDatos() }) {
            Text("Recargar")
        }


        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {

            val values = ContentValues().apply {
                put("claveMateria", "MAT999")
                put("materia", "Nueva materia")
                put("creditos", 5)
                put("calificacion", 100)
            }

            Log.d("INSERT", "$values")

            try {
                context.contentResolver.insert(
                    AppSiceContract.CardexContract.CONTENT_URI_CARDEX,
                    values
                )

                //Recargar después de insertar
                cargarDatos()

            } catch (e: Exception) {
                lista.add("Error insert: ${e.message}")
                Log.e("Error insert: ", "${e.message}")
            }

        }) {
            Text("Insertar")
        }

        LazyColumn {
            items(lista) { item ->
                Text(
                    text = item,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
        Log.d("Cantidad:",  "${lista.size}")
    }
}
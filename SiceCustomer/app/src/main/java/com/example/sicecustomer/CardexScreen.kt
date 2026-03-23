package com.example.sicecustomer

import android.net.Uri
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

@Composable
fun CardexClientScreen() {

    val context = LocalContext.current
    val lista = remember { mutableStateListOf<String>() }

    fun cargarDatos() {
        lista.clear()

        try {
            val cursor = context.contentResolver.query(
                Uri.parse("content://com.example.appsice.provider/cardex"),
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

        } catch (e: Exception) {
            lista.add("Error: ${e.message}")
        }
    }

    // Se ejecuta al iniciar
    LaunchedEffect(Unit) {
        cargarDatos()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Cardex (Cliente)",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { cargarDatos() }) {
            Text("Recargar")
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
    }
}
package com.example.tasks.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tasks.MainViewModel
import com.example.tasks.viewmodel.TaskViewModel

@Composable
fun SearchBar(
    viewModel: TaskViewModel,
    modifier: Modifier = Modifier
) {
    //NOs traemos searchText desde el viewModel
    val searchText by viewModel.searchText.collectAsState()
    //Buscador
    OutlinedTextField(
        value = searchText,
        onValueChange = viewModel::onSearchTextChange,
        //Agregar icono de busqueda
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Icono de busqueda")
        },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 56.dp),
            //.padding(vertical = 5.dp),
        placeholder = { Text(text = "Buscar...") },
        //Una sola linea
        singleLine = true,
        shape = RoundedCornerShape(percent = 50), //redondear
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
        )
    )
}
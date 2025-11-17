package com.example.tasks.ui.theme.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tasks.MainViewModel

@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    val viewModel = viewModel<MainViewModel>()
    val searchText by viewModel.searchText.collectAsState()
    //Buscador
    TextField(
        value = searchText,
        onValueChange = viewModel::onSearchTextChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Search") }
    )
}
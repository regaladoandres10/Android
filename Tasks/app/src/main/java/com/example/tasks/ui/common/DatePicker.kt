package com.example.tasks.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tasks.viewmodel.DateViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DatePickerFieldToModal( modifier: Modifier = Modifier ) {
    val viewModel = viewModel<DateViewModel>()
    val selectedDate by viewModel.selectedDate.collectAsState()
    val showModal by viewModel.showModal.collectAsState()

    OutlinedTextField(
        value = selectedDate?.let { convertMillisToDate(it) } ?: " ",
        onValueChange = { },
        label = { Text("Fecha") },
        readOnly = true,
        placeholder = { Text("DD/MM/AAAA") },
        trailingIcon = {
            IconButton( onClick = { viewModel.selectShowModal(true) } ) {
                Icon(Icons.Default.CalendarMonth, contentDescription = "Seleccionar fecha")
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable { viewModel.selectShowModal(true) }
    )

    if(showModal) {
        DatePickerModal(
            onDateSelected = {  viewModel.selectDate(it) },
            onDismiss = { viewModel.selectShowModal(false) }
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }

}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}
package com.example.dateandtime.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Composable
fun TimePickerFieldToModal( modifier: Modifier = Modifier ) {
    var selectedTime by remember { mutableStateOf<Long?>(null) }
    var showModal by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = selectedTime?.let { formatMillisToTime(it) } ?: "",
            onValueChange = { },
            label = { Text("Hora") },
            placeholder = { Text("HH:MM") },
            readOnly = true, //El usuario no puede escribir
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clickable { showModal = true }

        )
        if (showModal) {
            TimePickerDial(
                onConfirm = {
                    selectedTime = it
                    showModal = false
                },
                onDismiss = { showModal = false }
            )
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDial(
    onConfirm: (Long) -> Unit,
    onDismiss: () -> Unit,
) {
    //Obtener la hora actual
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    Dialog(
        onDismissRequest = onDismiss
    ) {
        TimePickerDialogContent(
            timePickerState = timePickerState,
            onConfirm = onConfirm,
            onDismiss = onDismiss
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialogContent(
    timePickerState: TimePickerState,
    onConfirm: (Long) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.extraLarge,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            TimePicker(state = timePickerState)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.End
            ) {
                //Boton de cancelar
                TextButton( onClick = onDismiss ) {
                    Text("Cancelar")
                }
                Spacer(modifier = Modifier.width(8.dp))
                //Boton de Ok
                TextButton( onClick = {
                    //Convertir la hora seleccionada a milisegundos
                    val selectedTimeCalendar = Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                        set(Calendar.MINUTE, timePickerState.minute)
                        set(Calendar.SECOND, 0)
                        set(Calendar.MILLISECOND, 0)
                    }
                    onConfirm(selectedTimeCalendar.timeInMillis)

                } ) {
                    Text("Ok")
                }
            }
        }
    }
}

fun formatMillisToTime(millis: Long): String {
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    return formatter.format(Date(millis))
}
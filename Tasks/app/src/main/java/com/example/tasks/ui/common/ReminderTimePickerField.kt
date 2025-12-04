package com.example.tasks.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Composable
fun ReminderTimePickerField(
    reminderTime: Long?,
    onTimeSelected: (Long?) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    //Mostrar la fecha y hora actual en el campo
    val formattedDateTime by remember(reminderTime) {
        derivedStateOf {
            if (reminderTime == null) {
                ""
            } else {
                val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                formatter.format(Date(reminderTime))
            }
        }
    }

    var showDatePicker by remember { mutableStateOf(false) }
    var tempSelectedDateMillis by remember { mutableStateOf<Long?>(null) }
    var showTimePicker by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = formattedDateTime,
        onValueChange = { },
        label = { Text("Recordatorio") },
        readOnly = true,
        placeholder = { Text("No establecido") },
        trailingIcon = {
            IconButton(onClick = { showDatePicker = true }) {
                Icon(Icons.Default.Alarm, contentDescription = "Seleccionar recordatorio")
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .clickable { showDatePicker = true }
    )

    //Modal de Fecha
    if (showDatePicker) {
        DatePickerModal(
            onDateSelected = { dateMillis ->
                tempSelectedDateMillis = dateMillis
                showDatePicker = false
                if (dateMillis != null) {
                    showTimePicker = true //Si se selecciona fecha, pasamos a la hora
                } else {
                    onTimeSelected(null) //Borrar el recordatorio si se selecciona nulo
                }
            },
            onDismiss = { showDatePicker = false }
        )
    }

    //Diálogo de Hora
    if (showTimePicker) {
        val timeDialogState = rememberMaterialDialogState()

        LaunchedEffect(Unit) {
            timeDialogState.show()
        }

        MaterialDialog(
            dialogState = timeDialogState,
            buttons = {
                positiveButton(text = "Aceptar") {
                    showTimePicker = false
                }
                negativeButton(text = "Cancelar") {
                    showTimePicker = false
                    //si cancela la hora, también reiniciamos la fecha temporal
                    tempSelectedDateMillis = null
                }
            }
        ) {
            timepicker(
                initialTime = LocalTime.now(),
                title = "Seleccionar la hora"
            ) { selectedTime ->
                //Combinar la fecha (tempSelectedDateMillis) con la hora (selectedTime)
                if (tempSelectedDateMillis != null) {
                    val date = Calendar.getInstance(TimeZone.getDefault()).apply {
                        timeInMillis = tempSelectedDateMillis!!
                        set(Calendar.HOUR_OF_DAY, selectedTime.hour)
                        set(Calendar.MINUTE, selectedTime.minute)
                        set(Calendar.SECOND, 0)
                        set(Calendar.MILLISECOND, 0)
                    }
                    // Enviar el nuevo timestamp Long al ViewModel
                    onTimeSelected(date.timeInMillis)
                }
            }
        }
    }
}
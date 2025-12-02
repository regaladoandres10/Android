package com.example.tasks.ui.common

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun TimesPicker(
    label: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    //Inicializamos con la hora actual
    var pickedTime by remember { mutableStateOf(LocalTime.now()) }
    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("hh:mm a")
                .format(pickedTime)
        }
    }

    val timeDialogState = rememberMaterialDialogState()

    OutlinedTextField(
        value = formattedTime,
        onValueChange = {  },
        readOnly = true,
        label = { Text(label) },
        placeholder = { Text("HH:mm") },
        trailingIcon = {
            IconButton(onClick = { timeDialogState.show() }) {
                Icon(Icons.Filled.Schedule,
                    "Seleccionar hora")
            }

        },
        modifier = Modifier
            .clickable{ timeDialogState.show() }
    )

    MaterialDialog(
        dialogState = timeDialogState,
        buttons = {
            positiveButton( text = "Aceptar"  ) {
                Toast.makeText(
                    context,
                    "Clicked ok",
                    Toast.LENGTH_SHORT
                ).show()
            }
            negativeButton(text = "Cancelar")
        }
    ) {
        timepicker(
            initialTime = pickedTime,
            title = "Seleccionar la hora",
            //timeRange = LocalTime.MIDNIGHT..LocalTime.NOON
        ) {
            pickedTime = it
        }
    }
}
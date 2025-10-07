package com.example.basicstatecodelab

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

/**
 * Esta clase nos permite obtener el id de la tarea (id)
 * Asi como descripción (label)
 */
class WellnessTask(
    val id: Int,
    val label: String,
    //Almacenar el estado de marcado
    initialChecked: Boolean = false,
) {
    var checked by mutableStateOf(initialChecked)
}

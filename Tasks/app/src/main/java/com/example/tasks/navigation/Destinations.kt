package com.example.tasks.navigation

object Destinations {
    //Main screens
    const val TASK_ROUTE = "tareas"
    const val NOTES_ROUTE = "notas"
    const val SETTINGS_ROUTE = "configuración"

    //Secondary screens
    const val CREATE_TASK_ROUTE = "crear_tarea"
    const val CREATE_NOTE_ROUTE = "crear_nota"
    const val TASK_DETAILS_ROUTE = "detalle_tarear"
    const val NOTE_DETAILS_ROUTE = "detalle_notas"
    const val NOTE_EDIT_ROUTE = "editar_nota"
    //IDS
    const val TASK_ID = "taskId"
    const val NOTE_ID = "noteId"

    //Arguments routes
    const val TASK_DETAILS_WITH_ARGS = "$TASK_DETAILS_ROUTE/{$TASK_ID}"
    const val CREATE_TASK_WITH_ARGS = "$CREATE_TASK_ROUTE?$TASK_ID={$TASK_ID}"
    const val NOTE_DETAILS_WITH_ARGS = "$NOTE_DETAILS_ROUTE/{$NOTE_ID}"
    const val EDIT_NOTE_WITH_ARGS = "$NOTE_EDIT_ROUTE/{$NOTE_ID}"
}
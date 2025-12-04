package com.example.tasks.data.local

enum class SortTypeTask {
    TODAS,
    PENDIENTES,
    COMPLETADAS
}

enum class SortTypeNote {
    TODAS
}

//Mapeo de índice a SortTypeTask
val sortTypeMap = mapOf(
    0 to SortTypeTask.TODAS,
    1 to SortTypeTask.PENDIENTES,
    2 to SortTypeTask.COMPLETADAS
)
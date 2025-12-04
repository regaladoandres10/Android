package com.example.tasks.models

data class Tarea(
    val id: Int = 0,
    val title: String,
//    val description: String? = null,
//    val dueDate: Long? = null,
    val isCompleted: Boolean,
    val createdAt: Long = System.currentTimeMillis()
) {
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "$title",
            "${title.first()}"
        )
        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}

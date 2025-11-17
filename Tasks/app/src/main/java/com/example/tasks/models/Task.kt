package com.example.tasks.models

data class Task(
    val id: Int = 0,
    val title: String,
//    val description: String? = null,
//    val type: TaskType? = null,
//    val dueDate: Long? = null,
//    val isCompleted: Boolean = false,
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

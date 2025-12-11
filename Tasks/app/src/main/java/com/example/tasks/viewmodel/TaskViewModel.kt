package com.example.tasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasks.data.local.SortTypeTask
import com.example.tasks.data.local.dao.TaskDao
import com.example.tasks.data.local.entities.Task
import com.example.tasks.data.local.events.TaskEvent
import com.example.tasks.data.local.state.TaskState
import com.example.tasks.static.FileType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class TaskViewModel(
    private val dao: TaskDao
): ViewModel() {

    //Estado de busquedas
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    //Función para actualizar el texto
    fun onSearchTextChange(text: String) {
        _searchText.value = text
        //Mostramos la carga si hay texto y el filtrado va a ocurrir
        if (text.isBlank()) {
            _isSearching.value = true
        } else {
            _isSearching.value = false
        }
    }

    private val _sortType = MutableStateFlow(SortTypeTask.TODAS)
    //Guardar el tipo de ordenamiento actual
    //Crear el flow de tareas filtradas y ordenadas
    private val _tasks = _sortType
        .flatMapLatest { sortType ->
            when (sortType) {
                SortTypeTask.TODAS -> dao.getAllTaskOrderByName()
                SortTypeTask.PENDIENTES -> dao.getEarringTask()
                SortTypeTask.COMPLETADAS -> dao.getCompletedTask()
            }
        }
        //Crear un flujo de estado
        //viewModelScope = Asegura que el flow esta activo mientras el viewModel vive.
        //SharingStarted.WhileSubscribed() = Optimiza el consumo
        .stateIn( viewModelScope, SharingStarted.WhileSubscribed(), emptyList() )

    //Crear el flow de tareas mostradas filtrado por texto
    val tasksDisplay = combine(_tasks, _searchText) { tasks, text ->
        //Logica de filtrado/no filtrado
        val filteredList = if (text.isBlank()) {
            tasks
        } else {
            //Realiza la busqueda
            tasks.filter { task ->
                (task.title ?: "").contains(text, ignoreCase = true) ||
                        (task.description ?: "").contains(text, ignoreCase = true)
            }
        }
        //Desactivar la bandera de carga
        _isSearching.value = false
        return@combine filteredList
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _state = MutableStateFlow(TaskState())
    val state = combine(_state, _sortType, tasksDisplay ) { state, sortType, tasks ->
        state.copy(
            tasks = tasks,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TaskState())


    //Cargar datos de la tarea o editarlos
    fun loadTaskForEdit(taskId: Int) {
        viewModelScope.launch {
            val task = dao.getTaskById(taskId)
            /*
            if (task != null) {
                _state.value = _state.value.copy(
                    taskToEditId = task.id,
                    title = task.title ?: "",
                    description = task.description ?: "",
                    dueDate = task.dueDate,
                    reminderTime = task.reminderTime,
                    filePath = task.filePath,
                    fileType = task.fileType ?: FileType.NONE,
                    isCompleted = task.isCompleted,
                    isAddingTask = true //Mostrar el modal o pantalla
                )
            }
            */
        }
    }

    fun onEvent( event: TaskEvent ){
        when(event) {
            is TaskEvent.DeleteTask -> {
                viewModelScope.launch {
                    dao.deleteTask(event.task)
                }
            }
            //Insertar tareas
            TaskEvent.SaveTask -> {
                //Desestructurar los campos del estado
                val title = state.value.title
                val description = state.value.description
                val dueDate = state.value.dueDate
                val isCompleted = state.value.isCompleted
                val createdAt = state.value.createdAt
                val reminderTime = state.value.reminderTime
                val filePath = state.value.filePath
                val fileType = state.value.fileType
                val taskId = state.value.taskToEditId ?: 0

                //Validar los campos
                if (title.isBlank()) {
                    return
                }
                //Crear el objeto Task
                val task = Task(
                    id = taskId,
                    title = title,
                    description = description,
                    dueDate = dueDate,
                    isCompleted = isCompleted,
                    createdAt = createdAt,
                    reminderTime = reminderTime,
                    filePath = filePath,
                    fileType = fileType
                )
                //Insertar y actualizar el estado
                viewModelScope.launch {
                    dao.upsertTask(task)
                    //Limpiar el estado despues de guardar, utilizar una nueva tarea
                    _state.update { it.copy(
                        title = "",
                        description = "",
                        dueDate = null,
                        reminderTime = null,
                        filePath = null,
                        fileType = FileType.NONE,
                        isAddingTask = false,
                        taskToEditId = null
                    ) }
                }
            }
            //Setters
            is TaskEvent.SetContent -> {
                _state.update { it.copy(
                    description = event.descripcion
                ) }
            }
            is TaskEvent.SetDueDate -> {
                _state.update { it.copy(
                    dueDate = event.dueDate
                ) }
            }
            is TaskEvent.SetFile -> {
                _state.update { it.copy(
                    filePath = event.path,
                    fileType = event.type ?: FileType.NONE
                ) }
            }
            is TaskEvent.SetIsCompleted -> {
                viewModelScope.launch {
                    //Actualiza la tarea existente marcandola como completada/incompleta
                    val updateTask = event.task.copy(
                        isCompleted = event.isCompleted
                    )
                    dao.upsertTask(updateTask)
                }
            }
            is TaskEvent.SetReminderTime -> {
                _state.update { it.copy(
                    reminderTime = event.time
                ) }
            }
            is TaskEvent.SetTitle -> {
                _state.update { it.copy(
                    title = event.title
                ) }
            }
            is TaskEvent.SortTasks -> {
                _sortType.value = event.sortType
            }
            TaskEvent.hideModal -> {
                _state.update { it.copy(
                    isAddingTask = false
                ) }
            }
            TaskEvent.showModal -> {
                _state.update { it.copy(
                    isAddingTask = true
                ) }
            }
            is TaskEvent.SetTaskToEditId -> {
                //Acceder al id
                val eventId = (event as TaskEvent.SetTaskToEditId).id
                _state.update { it.copy(
                  taskToEditId = eventId
                ) }
            }
        }
    }
}

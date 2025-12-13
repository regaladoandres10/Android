package com.example.tasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasks.data.local.SortTypeTask
import com.example.tasks.data.local.dao.TaskDao
import com.example.tasks.data.local.entities.Task
import com.example.tasks.data.local.events.TaskEvent
import com.example.tasks.data.local.state.TaskState
import com.example.tasks.static.FileType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalCoroutinesApi::class)
class TaskViewModel(
    private val dao: TaskDao
): ViewModel() {

    //Estado de busquedas
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    //Agregando a la busqueda un tiempo
    val searchQuery = _searchText
        //Esperar 300 milisegundos
        .debounce(300)
        //Evita emitir el mismo valor dos veces seguida
        .distinctUntilChanged()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    //Función para actualizar el texto
    fun onSearchTextChange(text: String) {
        _searchText.value = text
        _isSearching.value = text.isNotBlank()
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
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList() )

    //Crear el flow de tareas mostradas filtrado por texto
    val tasksDisplay = combine(_tasks, searchQuery) { tasks, text ->
        //Logica de filtrado/no filtrado
        if (text.isBlank()) {
            tasks
        } else {
            tasks.filter { task ->
                (task.title ?: "").contains(text, ignoreCase = true) ||
                        (task.description ?: "").contains(text, ignoreCase = true)
            }
        }
        //Efectos secundarios
    }.onEach {
        //Desactivar la bandera de carga
        _isSearching.value = false
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        emptyList())

    private val _state = MutableStateFlow(TaskState())
    val state = combine(_state, _sortType, tasksDisplay ) { state, sortType, tasks ->
        state.copy(
            tasks = tasks,
            sortType = sortType
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        TaskState())

    //Cargar datos de la tarea o editarlos
    fun loadTaskForEdit(taskId: Int) {
        viewModelScope.launch {
            val task = withContext(Dispatchers.IO) {
                dao.getTaskById(taskId)
            }

            task?.let {
                _state.value = _state.value.copy(
                    taskToEditId = it.id,
                    title = it.title.orEmpty(),
                    description = it.description.orEmpty(),
                    dueDate = it.dueDate,
                    reminderTime = it.reminderTime,
                    filePath = it.filePath,
                    fileType = it.fileType,
                    isCompleted = it.isCompleted,
                    isAddingTask = true
                )
            }
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
                val s = state.value
                //Validar los campos
                if (s.title.isBlank()) return

                val task = Task(
                    //Desestructurar los campos del estado
                    id = s.taskToEditId ?: 0,
                    title = s.title,
                    description = s.description,
                    dueDate = s.dueDate,
                    isCompleted = s.isCompleted,
                    createdAt = s.createdAt,
                    reminderTime = s.reminderTime,
                    filePath = s.filePath,
                    fileType = s.fileType
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

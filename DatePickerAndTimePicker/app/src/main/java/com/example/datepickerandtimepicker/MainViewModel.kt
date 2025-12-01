package com.example.datepickerandtimepicker

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    private val _showDatePicker = MutableStateFlow(false)
    val showDatePicker = _showDatePicker.asStateFlow()

    private val _selectedDate = MutableStateFlow<Long?>(null)
    val selectedDate = _selectedDate.asStateFlow()

    private val _showModal = MutableStateFlow(false)
    val showModal = _showModal.asStateFlow()

    fun selectShowModal(value: Boolean) {
        _showModal.value = value
    }

    fun selectDate(value: Long? = null) {
        _selectedDate.value = value
    }
}
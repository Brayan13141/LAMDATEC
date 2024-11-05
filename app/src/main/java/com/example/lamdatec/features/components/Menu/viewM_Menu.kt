package com.example.lamdatec.features.components.Menu

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class MenuViewModel : ViewModel() {
    // Estado de la selección del ítem
    private val _selectedItem = MutableStateFlow("INICIO")
    val selectedItem: MutableStateFlow<String> = _selectedItem

    // Función para actualizar el ítem seleccionado
    fun selectItem(item: String) {
        _selectedItem.value = item
    }
}

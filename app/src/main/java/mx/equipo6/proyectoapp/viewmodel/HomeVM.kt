package mx.equipo6.proyectoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.equipo6.proyectoapp.model.Advice
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel de la pantalla principal de la aplicación
 * @autor Equipo 6
 */

class HomeVM : ViewModel() {
    private val _adviceList = MutableStateFlow<List<Advice>>(emptyList())
    val adviceList: StateFlow<List<Advice>> get() = _adviceList

    init {
        fetchAdvice()
    }

    fun fetchAdvice() {
        viewModelScope.launch {
            // Simulate API call
            val adviceFromApi = listOf(
                Advice(1, "Stay positive."),
                Advice(2, "Work hard."),
                Advice(3, "Be kind."),
                Advice(4, "Be patient.")
            )
            _adviceList.value = adviceFromApi
        }
    }
}
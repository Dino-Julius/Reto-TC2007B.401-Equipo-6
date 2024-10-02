package mx.equipo6.proyectoapp.viewmodel

import android.content.Context
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mx.equipo6.proyectoapp.model.Advice

/**
 * ViewModel de la pantalla principal de la aplicación
 * @autor Ulises Jaramillo Portilla | A01798380.
 */

class HomeVM : ViewModel() {
    private val _adviceList = MutableStateFlow<List<Advice>>(emptyList())
    val adviceList: StateFlow<List<Advice>> get() = _adviceList

    init {
        fetchAdvice()
    }

    private fun fetchAdvice() {
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

    fun saveSelectedButtons(context: Context, selectedButtons: List<ImageVector>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                val buttonNames = selectedButtons.map { it.name }
                sharedPreferences.edit {
                    putStringSet("selected_buttons", buttonNames.toSet())
                }
            }
        }
    }

    fun loadSelectedButtons(context: Context, allUserButtons: List<ImageVector>, allShoppingButtons: List<ImageVector>): List<ImageVector> {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val buttonNames = sharedPreferences.getStringSet("selected_buttons", emptySet()) ?: emptySet()
        return buttonNames.mapNotNull { name ->
            allUserButtons.find { it.name == name } ?: allShoppingButtons.find { it.name == name }
        }
    }
}
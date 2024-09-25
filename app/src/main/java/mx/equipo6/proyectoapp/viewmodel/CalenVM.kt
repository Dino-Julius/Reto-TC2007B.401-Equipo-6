package mx.equipo6.proyectoapp.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mx.equipo6.proyectoapp.model.getSavedDates
import mx.equipo6.proyectoapp.model.saveSexualActivityDate
import java.util.*

class CalenVM : ViewModel() {

    // Estado reactivo para la fecha seleccionada
    private var selectedDateInMillis = mutableLongStateOf(0L)
        private set

    var selectedDate = mutableStateOf("")
        private set

    var calculatedDate = mutableStateOf("")
        private set

    // Estado reactivo para almacenar las fechas guardadas
    var savedDates = mutableStateOf(listOf<String>())
        private set

    // Función para actualizar la fecha seleccionada
    fun updateSelectedDate(dateInMillis: Long) {
        selectedDateInMillis.longValue = dateInMillis
        val calendar = Calendar.getInstance().apply { timeInMillis = dateInMillis }
        selectedDate.value = "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}"
    }

    // Función para calcular la fecha del próximo ciclo menstrual (28 días después)
    fun calculateNextCycle() {
        val calendar = Calendar.getInstance().apply {
            if (selectedDateInMillis.longValue != 0L) {
                timeInMillis = selectedDateInMillis.longValue
                add(Calendar.DAY_OF_YEAR, 28) // Sumar 28 días
            }
        }
        calculatedDate.value = "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}"
    }

    // Función para guardar la fecha de actividad sexual y actualizar la lista de fechas guardadas
    fun saveDate(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            if (selectedDateInMillis.longValue != 0L) {
                saveSexualActivityDate(context, selectedDateInMillis.longValue)
                // Actualizar la lista de fechas guardadas
                savedDates.value = getSavedDates(context)
            }
        }
    }

    // Función para cargar las fechas guardadas al abrir el diálogo
    fun loadSavedDates(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            savedDates.value = getSavedDates(context)
        }
    }
}

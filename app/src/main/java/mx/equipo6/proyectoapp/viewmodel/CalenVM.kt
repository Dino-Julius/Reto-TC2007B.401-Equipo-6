package mx.equipo6.proyectoapp.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mx.equipo6.proyectoapp.model.calculateFertileWindowFromModel
import mx.equipo6.proyectoapp.model.deleteSexualActivityDate
import mx.equipo6.proyectoapp.model.getSavedDates
import mx.equipo6.proyectoapp.model.getSavedDatesWithKeys
import mx.equipo6.proyectoapp.model.saveSexualActivityDate
import java.util.*

class CalenVM : ViewModel() {

    // Estado reactivo para la fecha seleccionada
    private var selectedDateInMillis = mutableLongStateOf(0L)
        private set

    // Estado reactivo para la fecha seleccionada en formato de cadena
    var selectedDate = mutableStateOf("")
        private set
    // Estado reactivo para la fecha calculada
    var calculatedDate = mutableStateOf("")
        private set

    // Estado reactivo para almacenar las fechas guardadas
    var savedDates = mutableStateOf(listOf<String>())
        private set

    // Estado reactivo para controlar si se debe mostrar la animación
    var showAnimation = mutableStateOf(false)
        private set

    // Estado reactivo para almacenar las fechas guardadas con su clave
    var savedDatesWithKeys = mutableStateOf(listOf<Pair<Long, String>>())
        private set

    // Estado para los días fértiles calculados
    private val _fertileWindow = MutableStateFlow<Pair<String, String>?>(null)
    val fertileWindow: StateFlow<Pair<String, String>?> = _fertileWindow

    // Para almacenar las fechas de pastilla de emergencia
    var savedEmergencyPillDates = mutableStateOf(listOf<Pair<Long, String>>())
        private set

    // Para almacenar la fecha de pastilla de emergencia
    fun updateSelectedDate(dateInMillis: Long) {
        selectedDateInMillis.longValue = dateInMillis
        val calendar = Calendar.getInstance().apply { timeInMillis = dateInMillis }
        selectedDate.value = "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}"
    }

    // Para calcular la fecha del siguiente ciclo
    fun calculateNextCycle() {
        val calendar = Calendar.getInstance().apply {
            if (selectedDateInMillis.longValue != 0L) {
                timeInMillis = selectedDateInMillis.longValue
                add(Calendar.DAY_OF_YEAR, 28) // Sumar 28 días
            }
        }
        calculatedDate.value = "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}"

        // Reiniciar y activar la animación
        viewModelScope.launch {
            showAnimation.value = false
            // Añadir un pequeño delay para reiniciar la animación
            kotlinx.coroutines.delay(100)
            showAnimation.value = true
        }
    }

    // Para guardar la fecha de actividad sexual
    fun saveDate(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            if (selectedDateInMillis.longValue != 0L) {
                saveSexualActivityDate(context, selectedDateInMillis.longValue)
                // Actualizar la lista de fechas guardadas
                savedDates.value = getSavedDates(context)
            }
        }
    }
    // Para cargar las fechas guardadas
    fun deleteDate(context: Context, dateInMillis: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteSexualActivityDate(context, dateInMillis)
            // Actualizar la lista de fechas guardadas
            val updatedDates = getSavedDatesWithKeys(context)
            savedDatesWithKeys.value = updatedDates
        }
    }
    // Para cargar las fechas guardadas
    fun loadSavedDatesWithKeys(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            savedDatesWithKeys.value = getSavedDatesWithKeys(context)
        }
    }

    // Función para calcular los días fértiles
    fun calculateFertileWindow(startDate: String) {
        val fertileDates = calculateFertileWindowFromModel(startDate)
        _fertileWindow.value = fertileDates
    }
    // Función para guardar la fecha de pastilla de emergencia
    fun calculateNextEmergencyPillDate(lastPillDate: String): String {
        val dateParts = lastPillDate.split("/")
        if (dateParts.size != 3) return "Fecha inválida"

        val day = dateParts[0].toIntOrNull()
        val month = dateParts[1].toIntOrNull()
        val year = dateParts[2].toIntOrNull()

        if (day == null || month == null || year == null) return "Fecha inválida"

        val calendar = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, day)
            set(Calendar.MONTH, month - 1)  // Los meses en Calendar son base 0
            set(Calendar.YEAR, year)
        }

        // Añadir un número de días arbitrario para el tiempo mínimo de espera (ejemplo: 30 días)
        calendar.add(Calendar.DAY_OF_YEAR, 30)

        return "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}"
    }

    // Función para guardar la fecha de pastilla de emergencia
    fun saveEmergencyPillDate(context: Context, dateInMillis: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val sharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
            with(sharedPreferences.edit()) {
                putLong("emergencyPillDate_$dateInMillis", dateInMillis)
                apply()
            }
        }
    }

    // Función para cargar las fechas de pastilla de emergencia
    fun loadSavedEmergencyPillDates(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val sharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
            val pillDates = sharedPreferences.all
                .filter { it.key.startsWith("emergencyPillDate_") }
                .mapNotNull { entry ->
                    val dateInMillis = entry.value as? Long ?: return@mapNotNull null
                    dateInMillis to Calendar.getInstance().apply { timeInMillis = dateInMillis }
                }
                .sortedByDescending { it.first }
                .map { (dateInMillis, calendar) ->
                    dateInMillis to "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}"
                }

            // Actualizamos el estado reactivo con las fechas cargadas
            savedEmergencyPillDates.value = pillDates
        }
    }

    // Función para eliminar la fecha de pastilla de emergencia
    fun deleteEmergencyPillDate(context: Context, dateInMillis: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val sharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
            with(sharedPreferences.edit()) {
                remove("emergencyPillDate_$dateInMillis")
                apply()
            }
            // Después de eliminar, cargamos nuevamente las fechas actualizadas
            loadSavedEmergencyPillDates(context)
        }
    }
}

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

/**
 * @author: Sebastian Espinoza
 * Clase para el ViewModel del calendario
 */
class CalenVM : ViewModel() {

    /**
     * @author: Sebastian Espinoza
     * Variable para almacenar la fecha seleccionada en milisegundos
     */
    private var selectedDateInMillis = mutableLongStateOf(0L)
        private set

    /**
     * @author: Sebastian Espinoza
     * Variable para almacenar la fecha seleccionada en formato de cadena
     */
    var selectedDate = mutableStateOf("")
        private set

    /**
     * @author: Sebastian Espinoza
     * Variable para almacenar la fecha estimada del siguiente ciclo menstrual en formato de cadena
     */
    var calculatedDate = mutableStateOf("")
        private set

    /**
     * @author: Sebastian Espinoza
     * Variable para almacenar las fechas guardadas en formato de cadena
     */
    var savedDates = mutableStateOf(listOf<String>())
        private set

    /**
     * @author: Sebastian Espinoza
     * Variable para controlar la animación de la fecha estimada del siguiente ciclo menstrual
     */
    var showAnimation = mutableStateOf(false)
        private set

    /**
     * @author: Sebastian Espinoza
     * Variable para almacenar las fechas guardadas con su respectiva clave en formato de cadena
     */
    var savedDatesWithKeys = mutableStateOf(listOf<Pair<Long, String>>())
        private set

    /**
     * @author: Sebastian Espinoza
     * Variable para almacenar los días fértiles en formato de cadena
     */
    private val _fertileWindow = MutableStateFlow<Pair<String, String>?>(null)
    val fertileWindow: StateFlow<Pair<String, String>?> = _fertileWindow

    /**
     * @author: Sebastian Espinoza
     * Variable para almacenar las fechas de pastilla de emergencia en formato de cadena
     */
    var savedEmergencyPillDates = mutableStateOf(listOf<Pair<Long, String>>())
        private set

    /**
     * @author: Sebastian Espinoza
     * @param: dateInMillis: Long
     * Funcion para actualizar la fecha seleccionada
     */
    fun updateSelectedDate(dateInMillis: Long) {
        selectedDateInMillis.longValue = dateInMillis
        val calendar = Calendar.getInstance().apply { timeInMillis = dateInMillis }
        selectedDate.value = "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}"
    }

    /**
     * @author: Sebastian Espinoza
     * Funcion para calcular la fecha estimada del siguiente ciclo menstrual
     */
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

    /**
     * @author: Sebastian Espinoza
     * @param: context: Context
     * Funcion para guardar la fecha de actividad sexual
     */
    fun saveDate(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            if (selectedDateInMillis.longValue != 0L) {
                saveSexualActivityDate(context, selectedDateInMillis.longValue)
                // Actualizar la lista de fechas guardadas
                savedDates.value = getSavedDates(context)
            }
        }
    }

    /**
     * @author: Sebastian Espinoza
     * @param: context: Context
     * @param: dateInMillis: Long
     * funcion para eliminar la fecha de actividad sexual
     */
    fun deleteDate(context: Context, dateInMillis: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteSexualActivityDate(context, dateInMillis)
            // Actualizar la lista de fechas guardadas
            val updatedDates = getSavedDatesWithKeys(context)
            savedDatesWithKeys.value = updatedDates
        }
    }

    /**
     * @author: Sebastian Espinoza
     * @param: context: Context
     * funcion para cargar las fechas guardadas
     */
    fun loadSavedDatesWithKeys(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            savedDatesWithKeys.value = getSavedDatesWithKeys(context)
        }
    }

    /**
     * @author: Sebastian Espinoza
     * @param: startDate: String
     * Funcion para calcular los días fértiles
     */
    fun calculateFertileWindow(startDate: String) {
        val fertileDates = calculateFertileWindowFromModel(startDate)
        _fertileWindow.value = fertileDates
    }

    /**
     * @author: Sebastian Espinoza
     * @param: lastPillDate: String
     * @return: String
     * Funcion para calcular la fecha de la próxima pastilla de emergencia
     */
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

    /**
     * @author: Sebastian Espinoza
     * @param: context: Context
     * @param: dateInMillis: Long
     * Funcion para guardar la fecha de la próxima pastilla de emergencia
     */
    fun saveEmergencyPillDate(context: Context, dateInMillis: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val sharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
            with(sharedPreferences.edit()) {
                putLong("emergencyPillDate_$dateInMillis", dateInMillis)
                apply()
            }
        }
    }

    /**
     * @author: Sebastian Espinoza
     * @param: context: Context
     * Funcion para cargar las fechas de pastilla de emergencia
     */
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

    /**
     * @author: Sebastian Espinoza
     * @param: context: Context
     * @param: dateInMillis: Long
     * Funcion para eliminar la fecha de la próxima pastilla de emergencia
     */
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

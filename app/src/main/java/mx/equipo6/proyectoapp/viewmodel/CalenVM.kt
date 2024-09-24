package mx.equipo6.proyectoapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mx.equipo6.proyectoapp.model.saveSexualActivityDate
import mx.equipo6.proyectoapp.model.getSavedDates
import java.util.*

class CalenVM : ViewModel() {
    var selectedDateInMillis: Long = 0L
    var selectedDate: String = ""
    var calculatedDate: String = ""

    var savedDates = listOf<String>()

    fun updateSelectedDate(dateInMillis: Long) {
        selectedDateInMillis = dateInMillis
        val calendar = Calendar.getInstance().apply { timeInMillis = dateInMillis }
        selectedDate = "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}"
    }

    fun calculateNextCycle() {
        val calendar = Calendar.getInstance().apply {
            if (selectedDateInMillis != 0L) {
                timeInMillis = selectedDateInMillis
                add(Calendar.DAY_OF_YEAR, 28)
            }
        }
        calculatedDate = "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}"
    }

    fun saveDate(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            if (selectedDateInMillis != 0L) {
                saveSexualActivityDate(context, selectedDateInMillis)
                savedDates = getSavedDates(context)
            }
        }
    }

    fun loadSavedDates(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            savedDates = getSavedDates(context)
        }
    }
}

package mx.equipo6.proyectoapp.model

import android.content.Context
import java.util.Calendar

fun saveSexualActivityDate(context: Context, dateInMillis: Long) {
    val sharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putLong("sexualActivityDate_$dateInMillis", dateInMillis)
        apply()
    }
}

fun getSavedDates(context: Context): List<String> {
    val sharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)

    return sharedPreferences.all
        .mapNotNull { entry ->
            val dateInMillis = entry.value as? Long ?: return@mapNotNull null
            dateInMillis to Calendar.getInstance().apply { timeInMillis = dateInMillis }
        }
        // Ordenar la lista por el valor de dateInMillis (el primer componente de la tupla)
        .sortedByDescending { it.first }
        .map { (_, calendar) ->
            "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}"
        }
}

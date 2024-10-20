package mx.equipo6.proyectoapp.model

import android.content.Context
import java.util.Calendar
/**
 * @author Sebastian Espinoza | A01750311
 * Función para guardar la fecha de actividad sexual en SharedPreferences
 * @param context: Context
 * @param dateInMillis: Long
 */
fun saveSexualActivityDate(context: Context, dateInMillis: Long) {
    val sharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putLong("sexualActivityDate_$dateInMillis", dateInMillis)
        apply()
    }
}
/**
 * @author Sebastian Espinoza  | A01750311
 * @param context: Context
 * Función para obtener las fechas guardadas de actividad sexual
 */
fun getSavedDates(context: Context): List<String> {
    val sharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)

    return sharedPreferences.all
        .filter { it.key.startsWith("sexualActivityDate_") }  // Filter only sexual activity dates
        .mapNotNull { entry ->
            val dateInMillis = entry.value as? Long ?: return@mapNotNull null
            dateInMillis to Calendar.getInstance().apply { timeInMillis = dateInMillis }
        }
        .sortedByDescending { it.first }
        .map { (_, calendar) ->
            "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}"
        }
}

/**
 * @author Sebastian Espinoza | A01750311
 * @param context: Context
 * @param dateInMillis: Long
 * Función para eliminar una fecha de actividad sexual
 */
fun deleteSexualActivityDate(context: Context, dateInMillis: Long) {
    val sharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        remove("sexualActivityDate_$dateInMillis")
        apply()
    }
}

/**
 * @author Sebastian Espinoza | A01750311
 * @param context: Context
 * Función para obtener las fechas guardadas con su clave
 */
fun getSavedDatesWithKeys(context: Context): List<Pair<Long, String>> {
    val sharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)

    return sharedPreferences.all
        .filter { it.key.startsWith("sexualActivityDate_") }  // Filtro solo actividad sexual
        .mapNotNull { entry ->
            val dateInMillis = entry.value as? Long ?: return@mapNotNull null
            dateInMillis to Calendar.getInstance().apply { timeInMillis = dateInMillis }
        }
        .sortedByDescending { it.first }  // Sort by most recent
        .map { (dateInMillis, calendar) ->
            dateInMillis to "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}"
        }
}

/**
 * @author: Sebastian Espinoza | A01750311
 * @param startDate: String
 * Función para calcular los días fértiles a partir de una fecha
 */
fun calculateFertileWindowFromModel(startDate: String): Pair<String, String>? {
    val dateParts = startDate.split("/")
    if (dateParts.size != 3) return null

    val day = dateParts[0].toIntOrNull()
    val month = dateParts[1].toIntOrNull()
    val year = dateParts[2].toIntOrNull()

    if (day == null || month == null || year == null) return null

    // Crear el objeto Calendar a partir de la fecha ingresada
    val calendar = Calendar.getInstance().apply {
        set(Calendar.DAY_OF_MONTH, day)
        set(Calendar.MONTH, month - 1) // Los meses en Calendar son base 0
        set(Calendar.YEAR, year)
    }

    // Calcular los días fértiles (aproximadamente del día 10 al 17)
    val fertileStart = calendar.clone() as Calendar
    fertileStart.add(Calendar.DAY_OF_YEAR, 10)

    val fertileEnd = calendar.clone() as Calendar
    fertileEnd.add(Calendar.DAY_OF_YEAR, 17)

    // Formatear las fechas para mostrarlas en el formato DD/MM/YYYY
    val startFertileDate = "${fertileStart.get(Calendar.DAY_OF_MONTH)}/${fertileStart.get(Calendar.MONTH) + 1}/${fertileStart.get(Calendar.YEAR)}"
    val endFertileDate = "${fertileEnd.get(Calendar.DAY_OF_MONTH)}/${fertileEnd.get(Calendar.MONTH) + 1}/${fertileEnd.get(Calendar.YEAR)}"

    return Pair(startFertileDate, endFertileDate)
}



package mx.equipo6.proyectoapp.model.products

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Clase de datos que representa un rating, puede ser utilizada con cualquier formato JSON (Temporalmente para la API de fakestore)
 * @author Julio Vivas
 * @param count Cantidad de calificaciones
 * @param rate Calificación
 */
@Parcelize
class Rating (
    @SerializedName("count")
    val count: Int,
    @SerializedName("rate")
    val rate: Double
) : Parcelable
package mx.equipo6.proyectoapp.model.products

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// Annotation to make the class parcelable
@Parcelize
// Data class representing a rating, can be used with any JSON format
class Rating (
    // Mapping JSON key "count" to property count
    @SerializedName("count")
    val count: Int,
    // Mapping JSON key "rate" to property rate
    @SerializedName("rate")
    val rate: Double
) : Parcelable // Making the class implement Parcelable interface
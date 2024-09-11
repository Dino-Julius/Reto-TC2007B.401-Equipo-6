package mx.equipo6.proyectoapp.model.products

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// Annotation to make the class parcelable
@Parcelize
// Data class representing a product, can be used with any JSON format, here it's for fakestore API
class Products (
    // Mapping JSON key "category" to property category
    @SerializedName("category")
    val category: String,
    // Mapping JSON key "description" to property description
    @SerializedName("description")
    val description: String,
    // Mapping JSON key "id" to property id
    @SerializedName("id")
    val id: Int,
    // Mapping JSON key "image" to property image
    @SerializedName("image")
    val image: String,
    // Mapping JSON key "price" to property price
    @SerializedName("price")
    val price: Double,
    // Mapping JSON key "rating" to property rating
    @SerializedName("rating")
    val rating: Rating,
    // Mapping JSON key "title" to property title
    @SerializedName("title")
    val title: String
) : Parcelable // Making the class implement Parcelable interface
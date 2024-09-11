package mx.equipo6.proyectoapp.model.products

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
* Clase de datos que representa un producto, puede ser utilizada con cualquier formato JSON (Temporalmente para la API de fakestore)
* @author Julio Vivas
* @param category Mapea la clave JSON "category" a la propiedad category
* @param description Mapea la clave JSON "description" a la propiedad description
* @param id Mapea la clave JSON "id" a la propiedad id
* @param image Mapea la clave JSON "image" a la propiedad image
* @param price Mapea la clave JSON "price" a la propiedad price
* @param rating Mapea la clave JSON "rating" a la propiedad rating
* @param title Mapea la clave JSON "title" a la propiedad title
*/
@Parcelize
class Products (
    @SerializedName("category")
    val category: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("rating")
    val rating: Rating,
    @SerializedName("title")
    val title: String
) : Parcelable
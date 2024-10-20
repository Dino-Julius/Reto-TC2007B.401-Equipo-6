package mx.equipo6.proyectoapp.model.products

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Data class que representa un producto
 * @property sku Código del producto
 * @property name Nombre del producto
 * @property price Precio del producto
 * @property description Descripción del producto
 * @property dimensions Dimensiones del producto
 * @property image_path Ruta de la imagen del producto
 * @property category Categoría del producto
 * @property rating Calificación del producto
 * @property favorite Indica si el producto es favorito
 * @author Julio Vivas | A01749879
 */
@Parcelize
class Products (
    @SerializedName("sku")
    val sku: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("description")
    val description: String,
    @SerializedName("dimensions")
    val dimensions: String,
    @SerializedName("image_path")
    val image_path: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("rating")
    val rating: Double,
    var favorite: Boolean = false
) : Parcelable
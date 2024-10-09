package mx.equipo6.proyectoapp.model.posts

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Clase de datos que representa un post, puede ser utilizada con cualquier formato JSON.
 * @author Ulises Jaramillo Portilla | A01798380
 * @param post_id ID del post.
 * @param title Título del post.
 * @param summary Resumen del post.
 * @param date Fecha de publicación del post.
 * @param category Categoría del post.
 * @param partner_email ID del partner que publicó el post.
 * @param file_path Ruta del archivo del post.
 * @param image_path Ruta de la imagen del post.
 */
@Parcelize
class Post(
    @SerializedName("post_id")
    val post_id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("summary")
    val summary: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("partner_email")
    val partner_email: String,
    @SerializedName("file_path")
    val file_path: String,
    @SerializedName("image_path")
    val image_path: String,
) : Parcelable
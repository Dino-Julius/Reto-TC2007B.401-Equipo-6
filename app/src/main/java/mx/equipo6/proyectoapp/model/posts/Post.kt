//package mx.equipo6.proyectoapp.model.posts
//
//import android.os.Parcelable
//import com.google.gson.annotations.SerializedName
//import kotlinx.parcelize.Parcelize
//
///**
// * Clase de datos que representa un post, puede ser utilizada con cualquier formato JSON.
// * @author Ulises Jaramillo Portilla | A01798380
// * @param author Mapea la clave JSON "author" a la propiedad author
// * @param content Mapea la clave JSON "content" a la propiedad content
// * @param summary Breve resumen del post
// * @param id Mapea la clave JSON "id" a la propiedad id
// * @param image Mapea la clave JSON "image" a la propiedad image
// * @param date Mapea la clave JSON "date" a la propiedad date
// * @param title Mapea la clave JSON "title" a la propiedad title
// * @param comments Lista de comentarios asociados al post
// */
//@Parcelize
//class Post(
//    @SerializedName("author")
//    val author: String,
//    @SerializedName("content")
//    val content: String,
//    @SerializedName("summary")
//    val summary: String,
//    @SerializedName("id")
//    val id: Int,
//    @SerializedName("image")
//    val image: String,
//    @SerializedName("date")
//    val date: String,
//    @SerializedName("title")
//    val title: String,
//    @SerializedName("comments")
//    val comments: List<Comment>
//) : Parcelable
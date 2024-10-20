package mx.equipo6.proyectoapp.model.auth

import com.google.gson.annotations.SerializedName

/**
 * Data class que representa un usuario.
 * @param firstName Nombre del usuario.
 * @param lastName Apellido del usuario.
 * @param birthDate Fecha de nacimiento del usuario.
 * @param gender Género del usuario.
 * @param phone Teléfono del usuario.
 * @param email Correo electrónico del usuario.
 * @param profilePic URL de la imagen de perfil del usuario.
 * @param shipping_adress Dirección de envío del usuario.
 * @param password Contraseña hasheada del usuario.
 * @author Julio Vivas | A01749879
 */
data class User(
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("birth_date") val birthDate: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("email") val email: String,
    @SerializedName("profile_pic") val profilePic: String,
    @SerializedName("shipping_address") val address: String,
    @SerializedName("password") val password: String
)
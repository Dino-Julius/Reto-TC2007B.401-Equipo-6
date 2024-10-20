package mx.equipo6.proyectoapp.model.profileData

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mx.equipo6.proyectoapp.api.RetrofitClient

/**
 * Data class que representa la información que se envía al servidor para editar el perfil de un usuario
 * @author Jesús Guzmán | A01799257
 * @param first_name Nombre del usuario
 * @param last_name Apellido del usuario
 * @param birth_date Fecha de nacimiento del usuario
 * @param gender Género del usuario
 * @param phone Teléfono del usuario
 * @param email Correo electrónico del usuario
 * @param password Contraseña del usuario
 * @param shipping_address Dirección de envío del usuario
 * @param profile_pic Imagen de perfil del usuario
 */
data class EditProfileRequest(
    val first_name: String?,
    val last_name: String?,
    val birth_date: String?,
    val gender: String?,
    val phone: String?,
    val email: String?,
    val password: String?,
    val shipping_address: String?,
    val profile_pic: String?
)

/**
 * Data class que representa la respuesta del servidor al intentar editar el perfil de un usuario
 * @author Jesús Guzmán | A01799257
 * @param success Indica si la petición fue exitosa
 * @param message Mensaje de respuesta del servidor
 * @param user Usuario con la información actualizada
 */
data class EditProfileResponse(
    val success: Boolean,
    val message: String,
    val user: User
)

/**
 * Función suspend que envía la información de un usuario al servidor para editar su perfil
 * @param editProfileRequest Información del usuario a editar
 * @return Mensaje de respuesta del servidor
 * @author Julio Vivas | A01749879
 */
suspend fun sendDataToServer(editProfileRequest: EditProfileRequest): String {
    return withContext(Dispatchers.IO) {
        try {
            val response = RetrofitClient.apiService.updateUser(editProfileRequest.email!!, editProfileRequest)
            if (response.success) {
                "Data sent successfully: ${response.message}"
            } else {
                "Error sending data: ${response.message}"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "Exception occurred: ${e.message}"
        }
    }
}
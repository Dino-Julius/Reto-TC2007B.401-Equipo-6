package mx.equipo6.proyectoapp.model.profileData

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mx.equipo6.proyectoapp.api.RetrofitClient

/**
 * Data class que representa la información que se envía al servidor para registrar un nuevo usuario
 * @param first_name Nombre del usuario
 * @param last_name Apellido del usuario
 * @param birth_date Fecha de nacimiento del usuario
 * @param gender Género del usuario
 * @param phone Número de teléfono del usuario
 * @param email Correo electrónico del usuario
 * @param password Contraseña del usuario
 * @param shipping_address Dirección de envío del usuario
 * @author Jesús Guzmán | A01799257
 */
data class SignUpRequest(
    val first_name: String,
    val last_name: String,
    val birth_date: String,
    val gender: String,
    val phone: String,
    val email: String,
    val password: String,
    val shipping_address: String
)

/**
 * Data class que representa la respuesta del servidor al enviar la información para registrar un nuevo usuario
 * @param success Indica si la operación fue exitosa
 * @param message Mensaje de respuesta del servidor
 * @param user Datos del usuario registrado
 * @author Jesús Guzmán | A01799257
 */
data class SignUpResponse(
    val success: Boolean,
    val message: String,
    val user: User
)

/**
 * Data class que representa los datos de un usuario
 * @param user_id ID del usuario
 * @param first_name Nombre del usuario
 * @param last_name Apellido del usuario
 * @param birth_date Fecha de nacimiento del usuario
 * @param gender Género del usuario
 * @param phone Número de teléfono del usuario
 * @param email Correo electrónico del usuario
 * @param password Contraseña del usuario
 * @param profile_pic Foto de perfil del usuario
 * @param shipping_address Dirección de envío del usuario
 * @author Jesús Guzmán | A01799257
 */
data class User(
    val user_id: String,
    val first_name: String,
    val last_name: String,
    val birth_date: String,
    val gender: String,
    val phone: String,
    val email: String,
    val password: String,
    val profile_pic: String,
    val shipping_address: String
)

/**
 * Función que envía la información de registro de un nuevo usuario al servidor
 * @param signUpRequest Datos del usuario a registrar
 * @return Mensaje de respuesta del servidor
 * @author Jesús Guzmán | A01799257
 */
suspend fun sendDataToServer(signUpRequest: SignUpRequest): String {
    return withContext(Dispatchers.IO) {
        try {
            val response = RetrofitClient.apiService.signUp(signUpRequest)
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